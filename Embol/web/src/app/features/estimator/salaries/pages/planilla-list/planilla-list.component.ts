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

@Component({
  selector: 'app-planilla-list',
  standalone: true,
  imports: [...PLANILLA_IMPORTS],
  providers: [DataMemoryStore],
  templateUrl: './planilla-list.component.html',
})
export class PlanillaListComponent implements OnInit {
  private planillaService = inject(PlanillaService);
  store = inject(DataMemoryStore<PlanillaRow>);

  dataTable = viewChild<DataTableComponent>('dataTable');

  loading = signal(true);
  error   = signal<string | null>(null);
  pageData = signal<Page<PlanillaRow> | null>(null);
  summaryData = signal<PlanillaSummary | null>(null);
  cargasData = signal<CargaSocial[]>([]);
  pageSize = signal<number>(5);

  // ── Dynamic Form State ──
  isFormOpen = signal(false);
  formConfig = signal<DynamicFormConfig>({
    title: '',
    fields: []
  });

  // ── Tabs ──
  tabs = signal<DataTableTab[]>([
    { id: 'sueldos',      label: 'Sueldos y Salarios', active: true, colorClass: 'btn-primary shadow-sm' },
    // { id: 'comisiones',   label: 'Comisiones',         active: false },
    { id: 'horas-extra',  label: 'Horas Extra',        active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'bonos',        label: 'Bonos',              active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'beneficios',   label: 'Beneficios',         active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'provisiones',  label: 'Provisiones/Cargas', active: false, colorClass: 'btn-primary shadow-sm' },
    { id: 'consolidado',  label: 'Consolidado CMO',    active: false, colorClass: 'btn-primary shadow-sm' },
  ]);

  // ── Summary Cards ──
  summaryCards = computed<DataTableSummaryCard[]>(() => {
    const summary = this.summaryData();
    if (!summary) return [];
    return [
      { label: 'Total Planilla Anual',  value: this.formatBs(summary.totalPlanillaAnual),  borderColor: '#f59e0b' },
      { label: 'Total Cargas Sociales', value: this.formatBs(summary.totalCargasSociales), borderColor: '#ef4444' },
      { label: 'Total Provisiones',     value: this.formatBs(summary.totalProvisiones),     borderColor: '#f97316' },
      { label: 'Gran Total CMO',        value: this.formatBs(summary.granTotalCMO),         borderColor: '#22c55e' },
    ];
  });

  // ── Column Definitions ──
  columns = signal<DataTableColumn<PlanillaRow>[]>([
    { key: 'nroPers',      header: 'N° Pers.',     type: 'text',     sticky: true, width: '80px' },
    { key: 'nombre',       header: 'Nombre',       type: 'text',     sticky: true, minWidth: '160px', cssClass: 'bg-red-200 font-semibold' },
    { key: 'familia',      header: 'Familia',      type: 'text',     width: '70px', align: 'center' },
    { key: 'cargo',        header: 'Cargo',        type: 'text',     minWidth: '180px' },
    { key: 'area',         header: 'Área',         type: 'badge',    width: '70px', align: 'center',
      badgeColorMap: {
        'ADM':  'bg-red-100 text-red-700',
        'PROD': 'bg-green-100 text-green-700',
        'COM':  'bg-blue-100 text-blue-700',
        'FIN':  'bg-purple-100 text-purple-700',
        'MANT': 'bg-teal-100 text-teal-700',
      }
    },
    { key: 'haberBasico',  header: 'Haber Básico', type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'ene',          header: 'Ene',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'feb',          header: 'Feb',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'mar',          header: 'Mar',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'abr',          header: 'Abr',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'may',          header: 'May',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'jun',          header: 'Jun',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'jul',          header: 'Jul',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'ago',          header: 'Ago',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'sep',          header: 'Sep',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'oct',          header: 'Oct',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'nov',          header: 'Nov',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'dic',          header: 'Dic',          type: 'currency', align: 'right', summable: true, editable: true },
    { key: 'totalAnual',   header: 'Total Anual',  type: 'currency', align: 'right', summable: true, cssClass: 'font-bold text-blue-700' },
    { key: 'cargasPorcentaje', header: 'Cargas (43.2%)', type: 'currency', align: 'right', summable: true, cssClass: 'text-brand' },
  ]);

  // ── Global Actions ──
  globalActions = signal<DataTableGlobalAction[]>([
    {
      label: 'Personal',
      colorClass: 'btn-primary',
      handler: () => this.onAddPersonal(),
    },
    {
      label: 'Ajustes',
      colorClass: 'btn-primary',
      handler: () => this.onOpenSettingsForm(),
    },
    {
      label: 'Guardar',
      colorClass: 'btn-primary',
      handler: () => this.onSave(),
    },
    {
      label: 'Exportar',
      colorClass: 'btn-primary',
      handler: () => this.onExport(),
    },
    {
      label: 'Recalcular',
      colorClass: 'btn-primary',
      handler: () => this.onRecalculate(),
    },
  ]);

  // ── Collapsible Sections ──
  collapsibleSections = signal<DataTableCollapsibleSection[]>([]);

  // ── Working data from store ──
  workingData = computed(() => this.store.data());
  recordCount = computed(() => this.store.data().length);
  lastUpdated = computed(() => '15-Jun-2026 14:32'); // TODO: Obtener del backend si es necesario

  ngOnInit(): void {
    this.loadPlanilla();
  }

  loadPlanilla(page: number = 0): void {
    this.loading.set(true);
    this.error.set(null);

    forkJoin({
      planillas: this.planillaService.getPlanillas(page, this.pageSize()),
      summary: this.planillaService.getSummary(),
      cargas: this.planillaService.getCargasSociales()
    }).subscribe({
      next: ({ planillas, summary, cargas }) => {
        this.pageData.set(planillas);
        this.summaryData.set(summary);
        this.cargasData.set(cargas);

        this.store.loadData(planillas.content);
        this.buildCollapsibleSections(cargas);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error loading data', err);
        this.error.set(err.message || 'Error al cargar los datos.');
        this.loading.set(false);
      }
    });
  }

  private buildCollapsibleSections(cargasSociales: CargaSocial[]): void {
    this.collapsibleSections.set([
      {
        title: 'Desglose de Cargas Sociales y Provisiones',
        expanded: false,
        columns: [
          { key: 'concepto',     header: 'Concepto',      type: 'text' },
          { key: 'porcentaje',   header: 'Porcentaje',    type: 'number', align: 'left', cssClass: 'text-brand font-bold' },
          { key: 'base',         header: 'Base',          type: 'text' },
          { key: 'montoMensual', header: 'Monto Mensual', type: 'currency', align: 'right' },
          { key: 'montoAnual',   header: 'Monto Anual',   type: 'currency', align: 'right' },
        ],
        data: cargasSociales,
      }
    ]);
  }

  // ── Cell Change Handler ──
  onCellChange(event: CellChangeEvent<PlanillaRow>): void {
    this.store.updateCell(event.row.id, event.column, event.newValue);
  }

  // ── Tab Change Handler ──
  onTabChange(tab: DataTableTab): void {
    const updated = this.tabs().map(t => ({ ...t, active: t.id === tab.id }));
    this.tabs.set(updated);
    // En producción: cargar datos del tab seleccionado
    console.log('Tab changed to:', tab.label);
  }

  onPageChange(page: number): void {
    console.log('Cambiando a página:', page);
    this.loadPlanilla(page);
  }

  onPageSizeChange(size: number): void {
    this.pageSize.set(size);
    this.loadPlanilla(0);
  }

  // ── Global Actions ──
  onSave(): void {
    if (!this.store.hasChanges()) {
      console.log('No hay cambios para guardar.');
      return;
    }

    const payload = this.store.getChangesPayload();
    console.log('Payload para backend:', payload);

    this.planillaService.saveChanges(payload).subscribe({
      next: () => {
        this.store.commitChanges();
        this.dataTable()?.clearDirtyState();
        console.log('Cambios guardados exitosamente.');
      },
      error: (err) => {
        console.error('Error al guardar:', err);
      }
    });
  }

  onExport(): void {
    console.log('Exportando planilla...');
    // TODO: Implementar exportación a Excel/CSV
  }

  onRecalculate(): void {
    console.log('Recalculando planilla...');
    // TODO: Implementar recálculo de totales
    this.loadPlanilla();
  }

  // ── Form Handlers ──
  onAddPersonal(): void {
    this.formConfig.set({
      title: 'Añadir Nuevo Personal',
      submitLabel: 'Crear Registro',
      fields: [
        { key: 'nroPers', label: 'Nro. Personal', type: 'number', required: true, colSpan: 1 },
        { key: 'nombre', label: 'Nombre Completo', type: 'text', required: true, colSpan: 1 },
        { key: 'familia', label: 'Familia', type: 'text', required: true, colSpan: 1 },
        { key: 'cargo', label: 'Cargo', type: 'text', required: true, colSpan: 1 },
        {
          key: 'area',
          label: 'Área',
          type: 'select',
          required: true,
          colSpan: 1,
          options: [
            { label: 'Administración', value: 'ADM' },
            { label: 'Producción', value: 'PROD' },
            { label: 'Comercial', value: 'COM' },
            { label: 'Finanzas', value: 'FIN' },
            { label: 'Mantenimiento', value: 'MANT' },
          ]
        },
        { key: 'haberBasico', label: 'Haber Básico', type: 'number', required: true, colSpan: 1 },
        { key: 'fechaIngreso', label: 'Fecha de Ingreso', type: 'datepicker', required: true, colSpan: 1 },
      ]
    });
    this.isFormOpen.set(true);
  }

  onOpenSettingsForm(): void {
    this.formConfig.set({
      title: 'Configuración de Planilla',
      submitLabel: 'Aplicar Cambios',
      fields: [
        {
          key: 'mes',
          label: 'Mes de Proceso',
          type: 'select',
          required: true,
          colSpan: 1,
          options: [
            { label: 'Enero', value: '1' },
            { label: 'Febrero', value: '2' },
            { label: 'Marzo', value: '3' },
            { label: 'Abril', value: '4' },
            { label: 'Mayo', value: '5' },
            { label: 'Junio', value: '6' },
            { label: 'Julio', value: '7' },
            { label: 'Agosto', value: '8' },
            { label: 'Septiembre', value: '9' },
            { label: 'Octubre', value: '10' },
            { label: 'Noviembre', value: '11' },
            { label: 'Diciembre', value: '12' },
          ]
        },
        { key: 'tipoCambio', label: 'Tipo de Cambio', type: 'number', required: true, colSpan: 1, value: 6.96 },
        { key: 'observaciones', label: 'Observaciones', type: 'textarea', colSpan: 2 },
      ]
    });
    this.isFormOpen.set(true);
  }

  onFormClose(): void {
    this.isFormOpen.set(false);
  }

  onFormSubmit(data: any): void {
    console.log('Nuevo personal recibido:', data);

    // Simular creación
    const newRow: PlanillaRow = {
      ...data,
      id: Math.floor(Math.random() * 10000),
      ene: data.haberBasico,
      feb: data.haberBasico,
      mar: data.haberBasico,
      abr: data.haberBasico,
      may: data.haberBasico,
      jun: data.haberBasico,
      jul: data.haberBasico,
      ago: data.haberBasico,
      sep: data.haberBasico,
      oct: data.haberBasico,
      nov: data.haberBasico,
      dic: data.haberBasico,
      totalAnual: data.haberBasico * 12,
      cargasPorcentaje: data.haberBasico * 12 * 0.432
    };

    // Actualizar el store local
    const currentData = [...this.store.data()];
    this.store.loadData([newRow, ...currentData]);

    this.isFormOpen.set(false);
  }

  // ── Helper ──
  private formatBs(value: number): string {
    const formatted = Math.round(value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    return `Bs ${formatted}`;
  }

}
