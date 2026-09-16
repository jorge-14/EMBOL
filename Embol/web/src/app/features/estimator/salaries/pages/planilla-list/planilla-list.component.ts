
import { Component, OnInit, inject, signal, computed, viewChild } from '@angular/core';
import { PLANILLA_IMPORTS } from '../../enums/planilla-imports';
import { PlanillaService } from '../../services/planilla.service';
import { DataMemoryStore } from '../../../../../shared/utils/data-memory.store';
import { PlanillaRow, CargaSocial, PlanillaSummary } from '../../models/planilla.model';
import { Page } from '../../../../../shared/models/pagination.model';
import { forkJoin } from 'rxjs';
import {
  DataTableColumn,
  DataTableTab,
  DataTableSummaryCard,
  DataTableGlobalAction,
  DataTableCollapsibleSection,
  CellChangeEvent,
} from '../../../../../shared/components/data-table/models/data-table.model';
import { DataTableComponent } from '../../../../../shared/components/data-table/data-table.component';
import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';

import { buildPlanillaColumns } from '../../configs/planilla-columns.config';
import { getAddPersonalFormConfig, getSettingsFormConfig } from '../../configs/planilla-forms.config';
import { buildPlanillaGlobalActions } from '../../configs/planilla-actions.config';
import { buildPlanillaSummaryCards } from '../../configs/planilla-summary.config';
import { buildPlanillaCollapsibles } from '../../configs/planilla-collapsibles.config';

@Component({
  selector: 'app-planilla-list',
  standalone: true,
  imports: [...PLANILLA_IMPORTS],
  providers: [DataMemoryStore],
  templateUrl: './planilla-list.component.html',
})
export class PlanillaListComponent implements OnInit {
  // ── 1. DEPENDENCIAS E INYECCIONES ──
  private planillaService = inject(PlanillaService);
  store = inject(DataMemoryStore<PlanillaRow>);
  dataTable = viewChild<DataTableComponent>('dataTable');

  // ── 2. ESTADO DEL BACKEND (Data) ──
  loading = signal(true);
  error   = signal<string | null>(null);
  pageData = signal<Page<PlanillaRow> | null>(null);
  summaryData = signal<PlanillaSummary | null>(null);

  // ── 3. ESTADO DE LA UI Y FILTROS ──
  isFormOpen = signal(false);
  formConfig = signal<DynamicFormConfig>({ title: '', fields: [] });
  pageSize = signal<number>(5);
  periodoActual = signal<'Q1'|'Q2'|'Q3'|'Q4'|'S1'|'S2'|'ANUAL'|'TRIMESTRAL'>('ANUAL');
  areaFilter = signal<string>('');
  cargoFilter = signal<string>('');
  appliedAreaFilter = signal<string>('');
  appliedCargoFilter = signal<string>('');
  collapsibleSections = signal<DataTableCollapsibleSection[]>([]);

  tabs = signal<DataTableTab[]>([
    { id: 'sueldos',      label: 'Sueldos y Salarios', active: true,  colorClass: 'btn-primary shadow-sm' },
    { id: 'horas-extra',  label: 'Horas Extra',        active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'bonos',        label: 'Bonos',              active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'beneficios',   label: 'Beneficios',         active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'provisiones',  label: 'Provisiones/Cargas', active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'consolidado',  label: 'Consolidado CMO',    active: false, colorClass: 'btn-primary shadow-sm' },
  ]);

  globalActions = computed<DataTableGlobalAction[]>(() => {
    return buildPlanillaGlobalActions({
      onAddPersonal: () => this.onAddPersonal(),
      onOpenSettings: () => this.onOpenSettingsForm(),
      onSave: () => this.onSave(),
      onExport: () => this.onExport(),
      onRecalculate: () => this.onRecalculate(),
    });
  });

  // ── 4. ESTADOS COMPUTADOS (Derivados) ──
  columns = computed<DataTableColumn<PlanillaRow>[]>(() => {
    const periodo = this.periodoActual(); // Listening a periodoActual
    return buildPlanillaColumns(periodo);
  });

  summaryCards = computed<DataTableSummaryCard[]>(() => {
    const dataSummary = this.summaryData(); // Listening a summaryData
    return buildPlanillaSummaryCards(dataSummary, (val) => this.formatBs(val));
  });

  areasDisponibles = computed(() => {
    const dataStore = this.store.data(); // Listening a store.data
    return [...new Set(dataStore.map(r => r.area))].filter(Boolean).sort();
  });

  cargosDisponibles = computed(() => {
    const dataStore = this.store.data(); // Listening a store.data
    const filtroArea = this.areaFilter(); // Listening a areaFilter

    let data = dataStore;
    if (filtroArea) {
      data = data.filter(r => r.area === filtroArea);
    }
    return [...new Set(data.map(r => r.cargo))].filter(Boolean).sort();
  });

  workingData = computed(() => {
    const dataStore = this.store.data(); // Listening a store.data
    return dataStore.map(r => ({
      ...r,
      q1: (r.ene || 0) + (r.feb || 0) + (r.mar || 0),
      q2: (r.abr || 0) + (r.may || 0) + (r.jun || 0),
      q3: (r.jul || 0) + (r.ago || 0) + (r.sep || 0),
      q4: (r.oct || 0) + (r.nov || 0) + (r.dic || 0),
    }));
  });

  recordCount = computed(() => {
    const dataWorking = this.workingData(); // Listening a workingData
    return dataWorking.length;
  });

  lastUpdated = computed(() => '15-Jun-2026 14:32');

  // ── 5. LIFECYCLE ──
  ngOnInit(): void {
    this.loadPlanilla();
  }

  // ── 6. API & DATA LOADING ──
  loadPlanilla(page: number = 0): void {
    this.loading.set(true);
    this.error.set(null);

    const area = this.appliedAreaFilter();
    const cargo = this.appliedCargoFilter();

    forkJoin({
      planillas: this.planillaService.getPlanillas(page, this.pageSize(), area, cargo),
      summary: this.planillaService.getSummary(),
      cargas: this.planillaService.getCargasSociales()
    }).subscribe({
      next: ({ planillas, summary, cargas }) => {
        this.pageData.set(planillas);
        this.summaryData.set(summary);
        this.store.loadData(planillas.content);
        this.collapsibleSections.set(buildPlanillaCollapsibles(cargas));
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading data', err);
        this.error.set(err.message || 'Error al cargar los datos.');
        this.loading.set(false);
      }
    });
  }

  onSave(): void {
    if (!this.store.hasChanges()) return;
    const payload = this.store.getChangesPayload();
    this.planillaService.saveChanges(payload).subscribe({
      next: () => {
        this.store.commitChanges();
        this.dataTable()?.clearDirtyState();
      },
      error: (err) => console.error('Error al guardar:', err)
    });
  }

  onExport(): void {
    console.log('Exportando planilla...');
  }

  onRecalculate(): void {
    this.loadPlanilla();
  }

  // ── 7. EVENTOS DE LA UI (TABLA) ──
  onCellChange(event: CellChangeEvent<PlanillaRow>): void {
    this.store.updateCell(event.row.id, event.column, event.newValue);
  }

  onTabChange(tab: DataTableTab): void {
    const tabsData = this.tabs();
    this.tabs.set(tabsData.map(t => ({ ...t, active: t.id === tab.id })));
  }

  onPageChange(page: number): void {
    this.loadPlanilla(page);
  }

  onPageSizeChange(size: number): void {
    this.pageSize.set(size);
    this.loadPlanilla(0);
  }

  // ── 8. FILTROS ──
  clearFilters(): void {
    this.areaFilter.set('');
    this.cargoFilter.set('');
    this.periodoActual.set('ANUAL');
    this.applyFilters();
  }

  applyFilters(): void {
    this.appliedAreaFilter.set(this.areaFilter());
    this.appliedCargoFilter.set(this.cargoFilter());
    this.loadPlanilla(0);
  }

  // ── 9. EVENTOS DE LA UI (FORMULARIOS) ──
  onAddPersonal(): void {
    this.formConfig.set(getAddPersonalFormConfig());
    this.isFormOpen.set(true);
  }

  onOpenSettingsForm(): void {
    this.formConfig.set(getSettingsFormConfig());
    this.isFormOpen.set(true);
  }

  onFormClose(): void {
    this.isFormOpen.set(false);
  }

  onFormSubmit(data: any): void {
    const newRow: PlanillaRow = {
      ...data,
      id: Math.floor(Math.random() * 10000),
      ene: data.haberBasico, feb: data.haberBasico, mar: data.haberBasico,
      abr: data.haberBasico, may: data.haberBasico, jun: data.haberBasico,
      jul: data.haberBasico, ago: data.haberBasico, sep: data.haberBasico,
      oct: data.haberBasico, nov: data.haberBasico, dic: data.haberBasico,
      totalAnual: data.haberBasico * 12,
      cargasPorcentaje: data.haberBasico * 12 * 0.432
    };

    const currentData = [...this.store.data()];
    this.store.loadData([newRow, ...currentData]);
    this.isFormOpen.set(false);
  }

  // ── 10. HELPERS ──
  formatBs(value: number): string {
    const formatted = Math.round(value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    return `Bs ${formatted}`;
  }

  toggleSection(section: DataTableCollapsibleSection): void {
    section.expanded = !section.expanded;
  }
}
