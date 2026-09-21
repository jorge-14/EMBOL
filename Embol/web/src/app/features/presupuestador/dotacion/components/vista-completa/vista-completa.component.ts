import { Component, OnInit, signal, computed, Input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DotacionService } from '../../../../../core/services/dotacion.service';
import { EmpleadoDotacion, GestionDetalle, MovimientoPlanificado } from '../../models/dotacion.model';
import { DataTableComponent } from '../../../../../shared/components/data-table/data-table.component';
import { VISTA_COMPLETA_COLUMNS } from '../../configs/vista-completa/vista-completa-columns.config';
import { MOVIMIENTOS_COLUMNS } from '../../configs/vista-completa/movimientos-columns.config';
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { HorizontalSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { SelectOption } from '../../../../../shared/components/horizontal-controls/models/select-option.model';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-vista-completa',
  standalone: true,
  imports: [CommonModule, DataTableComponent, HorizontalSelectComponent, DialogModule, FormsModule],
  templateUrl: './vista-completa.component.html',
  styleUrl: './vista-completa.component.scss'
})
export class VistaCompletaComponent implements OnInit {

  @Input({ required: true }) set gestionId(val: number) {
    this._gestionId.set(val);
    this.loadData(val);
  }
  
  @Input() set initialDesde(val: string | undefined) {
    if (val) this.desdeMes.set(val);
  }
  
  @Input() set initialHasta(val: string | undefined) {
    if (val) this.hastaMes.set(val);
  }

  back = output<void>();

  _gestionId = signal<number | null>(null);
  mesSeleccionado = signal<string>('Febrero 2025');
  gestionInfo = signal<GestionDetalle | null>(null);
  empleados = signal<EmpleadoDotacion[]>([]);
  loading = signal<boolean>(false);

  // Filtros
  filtroArea = signal<string>('Todas');
  areaOptions: SelectOption[] = [
    { label: 'Todas', value: 'Todas' },
    { label: 'ADM', value: 'ADM' },
    { label: 'COM', value: 'COM' },
    { label: 'LOG', value: 'LOG' },
    { label: 'PROD MOD', value: 'PROD MOD' },
    { label: 'PROD MOI', value: 'PROD MOI' }
  ];

  plantaOptions: SelectOption[] = [
    { label: 'CBB — Cochabamba', value: 'CBB — Cochabamba' },
    { label: 'LPZ — La Paz', value: 'LPZ — La Paz' },
    { label: 'SCZ — Santa Cruz', value: 'SCZ — Santa Cruz' }
  ];

  mesesAbreviadosOptions: SelectOption[] = [
    { label: 'Ene', value: 'Ene' },
    { label: 'Feb', value: 'Feb' },
    { label: 'Mar', value: 'Mar' },
    { label: 'Abr', value: 'Abr' },
    { label: 'May', value: 'May' },
    { label: 'Jun', value: 'Jun' },
    { label: 'Jul', value: 'Jul' },
    { label: 'Ago', value: 'Ago' },
    { label: 'Sep', value: 'Sep' },
    { label: 'Oct', value: 'Oct' },
    { label: 'Nov', value: 'Nov' },
    { label: 'Dic', value: 'Dic' }
  ];

  empleadosFiltrados = computed(() => {
    const data = this.empleados();
    const filtro = this.filtroArea();

    if (filtro === 'Todas') {
      return data;
    }

    return data.filter(e => e.area === filtro);
  });

  // Rango Modal
  showRangeModal = signal(false);
  showAddMovimientoModal = signal(false);

  nuevoMovimiento = signal<Partial<MovimientoPlanificado>>({
    tipo: 'Desfase',
    nombre: '',
    cargo: '',
    area: 'ADM',
    planta: 'CBB — Cochabamba',
    observaciones: ''
  });

  mesesOptions: SelectOption[] = [
    { label: 'Enero', value: 'Enero' },
    { label: 'Febrero', value: 'Febrero' },
    { label: 'Marzo', value: 'Marzo' },
    { label: 'Abril', value: 'Abril' },
    { label: 'Mayo', value: 'Mayo' },
    { label: 'Junio', value: 'Junio' },
    { label: 'Julio', value: 'Julio' },
    { label: 'Agosto', value: 'Agosto' },
    { label: 'Septiembre', value: 'Septiembre' },
    { label: 'Octubre', value: 'Octubre' },
    { label: 'Noviembre', value: 'Noviembre' },
    { label: 'Diciembre', value: 'Diciembre' }
  ];
  desdeMes = signal('Enero');
  hastaMes = signal('Septiembre');

  // Agrupación
  agrupacionSeleccionada = signal<string>('Tipo');
  agrupacionOptions = [
    { label: 'Tipo', value: 'Tipo' },
    { label: 'Área', value: 'Área' },
    { label: 'Familia a cargo', value: 'Familia a cargo' },
    { label: 'Categoría', value: 'Categoría' },
    { label: 'Centro de costo', value: 'Centro de costo' },
    { label: 'Cargo', value: 'Cargo' }
  ];

  datosAgrupados = computed(() => {
    const empleados = this.empleadosFiltrados();
    const agrupacion = this.agrupacionSeleccionada();
    const counts: Record<string, number> = {};

    empleados.forEach(emp => {
      let key = '';
      switch (agrupacion) {
        case 'Tipo': key = emp.tipo; break;
        case 'Área': key = emp.area; break;
        case 'Familia a cargo': key = emp.familiaCargo.toString(); break;
        case 'Categoría': key = emp.categoria; break;
        case 'Centro de costo': key = emp.centroCosto; break;
        case 'Cargo': key = emp.cargo; break;
        default: key = 'Otro';
      }
      counts[key] = (counts[key] || 0) + 1;
    });

    const entries = Object.entries(counts).map(([label, count]) => ({ label, count }));
    // Ordenar por cuenta descendente
    return entries.sort((a, b) => b.count - a.count);
  });

  maxCount = computed(() => {
    const datos = this.datosAgrupados();
    if (datos.length === 0) return 0;
    return Math.max(...datos.map(d => d.count));
  });

  columns = VISTA_COMPLETA_COLUMNS;
  movimientosColumns = MOVIMIENTOS_COLUMNS;

  movimientos = signal<MovimientoPlanificado[]>([]);
  filtroMovimiento = signal<string>('Todos');

  movimientosFiltrados = computed(() => {
    const data = this.movimientos();
    const filtro = this.filtroMovimiento();

    if (filtro === 'Todos') {
      return data;
    }

    return data.filter(m => m.tipo === filtro);
  });

  rowActions: DataTableRowAction<EmpleadoDotacion>[] = [
    {
      icon: 'pi pi-ellipsis-h',
      tooltip: 'Movimientos',
      handler: (row) => console.log('Movimientos', row)
    }
  ];

  constructor(
    private service: DotacionService
  ) {}

  ngOnInit(): void {
    // Already updating inside inputs if needed, but we ensure to update month text.
    if (this._gestionId()) {
      this.updateMesSeleccionado();
    }
  }

  loadData(id: number): void {
    this.loading.set(true);
    this.service.getGestionById(id).subscribe(gestion => {
      if (gestion) {
        this.gestionInfo.set(gestion);
        this.updateMesSeleccionado();
      }
    });

    // In a real app we would call getEmpleadosByGestion(id), but we use mock
    const empleadosData = this.service.getEmpleadosMock();
    this.empleados.set(empleadosData);
    this.loading.set(false);

    const movsData = this.service.getMovimientosMock();
    this.movimientos.set(movsData);
  }

  updateMesSeleccionado(): void {
    const anio = this.gestionInfo()?.anio || '';
    if (this.desdeMes() === this.hastaMes()) {
      this.mesSeleccionado.set(`${this.desdeMes()} ${anio}`);
    } else {
      this.mesSeleccionado.set(`${this.desdeMes()} - ${this.hastaMes()} ${anio}`);
    }
  }

  goBack(): void {
    this.back.emit();
  }

  openRangeModal(): void {
    this.showRangeModal.set(true);
  }

  closeRangeModal(): void {
    this.showRangeModal.set(false);
  }

  verConsolidado(): void {
    this.updateMesSeleccionado();
    this.closeRangeModal();
  }

  openAddMovimientoModal(): void {
    this.nuevoMovimiento.set({
      tipo: 'Desfase',
      nombre: '',
      cargo: '',
      area: 'ADM',
      planta: 'CBB — Cochabamba',
      observaciones: ''
    });
    this.showAddMovimientoModal.set(true);
  }

  closeAddMovimientoModal(): void {
    this.showAddMovimientoModal.set(false);
  }

  saveMovimiento(): void {
    const mov = this.nuevoMovimiento() as MovimientoPlanificado;
    mov.id = Math.floor(Math.random() * 1000) + 100;

    this.movimientos.update(prev => [mov, ...prev]);
    this.closeAddMovimientoModal();
  }

  setTipoMovimiento(tipo: any): void {
    this.nuevoMovimiento.update(prev => ({ ...prev, tipo }));
  }
}
