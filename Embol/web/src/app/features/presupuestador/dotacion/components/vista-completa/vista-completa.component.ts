import { CommonModule } from '@angular/common';
import { Component, computed, input, OnInit, output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { DotacionService } from '../../../../../core/services/dotacion.service';
import { DataTableComponent } from '../../../../../shared/components/data-table/data-table.component';
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { HorizontalSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { SelectOption } from '../../../../../shared/components/horizontal-controls/models/select-option.model';
import { LoadingScreenComponent } from '../../../../../shared/components/loading-screen/loading-screen';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { MOVIMIENTOS_COLUMNS } from '../../configs/vista-completa/movimientos-columns.config';
import { VISTA_COMPLETA_COLUMNS } from '../../configs/vista-completa/vista-completa-columns.config';
import { EmpleadoDotacion, EstadoMesDotacion, FuenteDotacion, GestionDotacion, MovimientoPlanificado } from '../../models/dotacion.model';

@Component({
  selector: 'app-vista-completa',
  standalone: true,
  imports: [CommonModule, DataTableComponent, HorizontalSelectComponent, LoadingScreenComponent, DialogModule, FormsModule],
  templateUrl: './vista-completa.component.html',
  styleUrl: './vista-completa.component.scss'
})
export class VistaCompletaComponent implements OnInit {
  readonly gestion = input.required<GestionDotacion>();
  readonly businessPlanByMonth = input<Record<string, EmpleadoDotacion[]>>({});
  readonly realDataByMonth = input<Record<string, EmpleadoDotacion[]>>({});
  readonly monthStates = input<Record<string, EstadoMesDotacion>>({});
  readonly initialDesde = input<string | undefined>(undefined);
  readonly initialHasta = input<string | undefined>(undefined);
  readonly back = output<void>();
  readonly syncCompleted = output<{ month: string; data: EmpleadoDotacion[] }>();

  readonly selectedTab = signal<FuenteDotacion>('datos-reales');
  readonly desdeMes = signal('Enero');
  readonly hastaMes = signal('Enero');
  readonly filtroArea = signal('Todas');
  readonly search = signal('');
  readonly page = signal(0);
  readonly pageSize = signal(10);
  readonly loading = signal(false);
  readonly syncError = signal('');
  readonly showRangeModal = signal(false);
  readonly showAddMovimientoModal = signal(false);
  readonly agrupacionSeleccionada = signal('Tipo');
  readonly filtroMovimiento = signal('Todos');
  readonly movimientos = signal<MovimientoPlanificado[]>([]);

  readonly columns = VISTA_COMPLETA_COLUMNS;
  readonly movimientosColumns = MOVIMIENTOS_COLUMNS;
  readonly mesesOptions: SelectOption[] = [
    { label: 'Enero', value: 'Enero' }, { label: 'Febrero', value: 'Febrero' },
    { label: 'Marzo', value: 'Marzo' }, { label: 'Abril', value: 'Abril' },
    { label: 'Mayo', value: 'Mayo' }, { label: 'Junio', value: 'Junio' },
    { label: 'Julio', value: 'Julio' }, { label: 'Agosto', value: 'Agosto' },
    { label: 'Septiembre', value: 'Septiembre' }, { label: 'Octubre', value: 'Octubre' },
    { label: 'Noviembre', value: 'Noviembre' }, { label: 'Diciembre', value: 'Diciembre' }
  ];
  readonly areaOptions: SelectOption[] = [
    { label: 'Todas', value: 'Todas' }, { label: 'ADM', value: 'ADM' },
    { label: 'COM', value: 'COM' }, { label: 'LOG', value: 'LOG' },
    { label: 'PROD MOD', value: 'PROD MOD' }, { label: 'PROD MOI', value: 'PROD MOI' }
  ];
  readonly plantaOptions: SelectOption[] = [
    { label: 'CBB — Cochabamba', value: 'CBB — Cochabamba' },
    { label: 'LPZ — La Paz', value: 'LPZ — La Paz' },
    { label: 'SCZ — Santa Cruz', value: 'SCZ — Santa Cruz' }
  ];
  readonly agrupacionOptions = ['Tipo', 'Área', 'Familia a cargo', 'Categoría', 'Centro de costo', 'Cargo'];

  readonly nuevoMovimiento = signal<Partial<MovimientoPlanificado>>({
    tipo: 'Desfase', nombre: '', cargo: '', area: 'ADM', planta: 'CBB — Cochabamba', observaciones: ''
  });

  readonly activeMonths = computed(() => {
    const from = this.mesesOptions.findIndex(item => item.value === this.desdeMes());
    const to = this.mesesOptions.findIndex(item => item.value === this.hastaMes());
    const start = Math.max(0, Math.min(from, to));
    const end = Math.max(from, to, 0);
    return this.mesesOptions.slice(start, end + 1).map(item => item.value);
  });
  readonly mesSeleccionado = computed(() => {
    const months = this.activeMonths();
    const period = months.length === 1 ? months[0] : `${months[0]} - ${months[months.length - 1]}`;
    return `${period} ${this.gestion().anio}`;
  });
  readonly monthState = computed<EstadoMesDotacion>(() => {
    if (this.loading()) return 'Sincronizando';
    return this.monthStates()[this.activeMonths()[0]] ?? 'Datos reales pendientes';
  });
  readonly businessPlan = computed(() => this.rowsForMonths(this.businessPlanByMonth()));
  readonly actualData = computed(() => this.rowsForMonths(this.realDataByMonth()));
  readonly sourceData = computed(() => this.selectedTab() === 'business-plan' ? this.businessPlan() : this.actualData());
  readonly sourceAvailable = computed(() => this.selectedTab() === 'business-plan' || this.monthState() === 'Sincronizado');
  readonly empleadosFiltrados = computed(() => {
    const query = this.search().trim().toLocaleLowerCase();
    const area = this.filtroArea();
    return this.sourceData().filter(employee => {
      const matchesArea = area === 'Todas' || employee.area === area;
      const matchesSearch = !query || [employee.nombre, employee.ci, employee.cargo]
        .some(value => String(value ?? '').toLocaleLowerCase().includes(query));
      return matchesArea && matchesSearch;
    });
  });
  readonly pageInfo = computed<PageMetadata>(() => {
    const totalElements = this.empleadosFiltrados().length;
    const totalPages = Math.ceil(totalElements / this.pageSize());
    const number = Math.min(this.page(), Math.max(0, totalPages - 1));
    return { size: this.pageSize(), number, totalElements, totalPages };
  });
  readonly pagedEmployees = computed(() => {
    const info = this.pageInfo();
    return this.empleadosFiltrados().slice(info.number * info.size, (info.number + 1) * info.size);
  });
  readonly comparisonData = computed(() => {
    const bp = this.groupCounts(this.businessPlan());
    const actual = this.groupCounts(this.actualData());
    return [...new Set([...Object.keys(bp), ...Object.keys(actual)])]
      .map(label => ({ label, businessPlan: bp[label] ?? 0, datosReales: actual[label] ?? 0 }))
      .sort((a, b) => Math.max(b.businessPlan, b.datosReales) - Math.max(a.businessPlan, a.datosReales));
  });
  readonly maxCount = computed(() => Math.max(0, ...this.comparisonData().flatMap(item => [item.businessPlan, item.datosReales])));
  readonly movimientosFiltrados = computed(() => this.filtroMovimiento() === 'Todos'
    ? this.movimientos()
    : this.movimientos().filter(item => item.tipo === this.filtroMovimiento()));

  readonly rowActions: DataTableRowAction<EmpleadoDotacion>[] = [{
    icon: 'pi pi-ellipsis-h', tooltip: 'Movimientos', handler: row => console.log('Movimientos', row)
  }];

  constructor(private readonly service: DotacionService) {
    this.movimientos.set(this.service.getMovimientosMock());
  }

  ngOnInit(): void {
    this.desdeMes.set(this.initialDesde() ?? 'Enero');
    this.hastaMes.set(this.initialHasta() ?? this.initialDesde() ?? 'Enero');
  }

  private rowsForMonths(source: Record<string, EmpleadoDotacion[]>): EmpleadoDotacion[] {
    const months = this.activeMonths();
    return months.flatMap((month, monthIndex) => (source[month] ?? []).map(employee => ({
      ...employee,
      id: months.length === 1 ? employee.id : (monthIndex + 1) * 1_000_000 + employee.id
    })));
  }

  private groupCounts(rows: EmpleadoDotacion[]): Record<string, number> {
    const counts: Record<string, number> = {};
    for (const employee of rows) {
      let key = '';
      switch (this.agrupacionSeleccionada()) {
        case 'Tipo': key = employee.tipo; break;
        case 'Área': key = employee.area; break;
        case 'Familia a cargo': key = String(employee.familiaCargo); break;
        case 'Categoría': key = employee.categoria; break;
        case 'Centro de costo': key = employee.centroCosto; break;
        case 'Cargo': key = employee.cargo; break;
      }
      const label = key || 'Sin especificar';
      counts[label] = (counts[label] ?? 0) + 1;
    }
    return counts;
  }

  setTab(tab: FuenteDotacion): void { this.selectedTab.set(tab); this.page.set(0); }
  setSearch(value: string): void { this.search.set(value); this.page.set(0); }
  setArea(value: string): void { this.filtroArea.set(value); this.page.set(0); }
  onPageChange(page: number): void { this.page.set(page); }
  onPageSizeChange(size: number): void { this.pageSize.set(size); this.page.set(0); }
  goBack(): void { this.back.emit(); }
  openRangeModal(): void {
    this.desdeMes.set(this.activeMonths()[0]);
    this.hastaMes.set(this.activeMonths()[this.activeMonths().length - 1]);
    this.showRangeModal.set(true);
  }
  closeRangeModal(): void { this.showRangeModal.set(false); }
  verConsolidado(): void { this.showRangeModal.set(false); this.page.set(0); }

  synchronize(): void {
    const month = this.activeMonths()[0];
    if (!month || this.loading()) return;
    this.loading.set(true);
    this.syncError.set('');
    setTimeout(() => {
      try {
        const data = this.service.getEmpleadosMock().map(employee => ({ ...employee }));
        this.syncCompleted.emit({ month, data });
      } catch {
        this.syncError.set('No se pudo sincronizar la dotación con SAP.');
      } finally {
        this.loading.set(false);
      }
    }, 700);
  }

  openAddMovimientoModal(): void {
    this.nuevoMovimiento.set({ tipo: 'Desfase', nombre: '', cargo: '', area: 'ADM', planta: 'CBB — Cochabamba', observaciones: '' });
    this.showAddMovimientoModal.set(true);
  }
  closeAddMovimientoModal(): void { this.showAddMovimientoModal.set(false); }
  saveMovimiento(): void {
    const movement = { ...this.nuevoMovimiento(), id: Date.now() } as MovimientoPlanificado;
    this.movimientos.update(items => [movement, ...items]);
    this.closeAddMovimientoModal();
  }
  setTipoMovimiento(tipo: MovimientoPlanificado['tipo']): void {
    this.nuevoMovimiento.update(movement => ({ ...movement, tipo }));
  }
}
