import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DotacionService } from '../../../../../core/services/dotacion.service';
import { EmpleadoDotacion, EstadoMesDotacion, GestionDotacion, VistaPreviaImportacion } from '../../models/dotacion.model';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';
import { GestionDetalleComponent } from '../../components/gestion-detalle/gestion-detalle.component';
import { VistaCompletaComponent } from '../../components/vista-completa/vista-completa.component';
import { DialogModule } from 'primeng/dialog';
import { distributeBusinessPlan, parseAnnualBusinessPlan } from '../../xlsx-import.util';

type ViewState = 'lista' | 'detalle' | 'completa';

@Component({
  selector: 'app-dotacion',
  standalone: true,
  imports: [CommonModule, CheckboxModule, FormsModule, DialogModule, GestionDetalleComponent, VistaCompletaComponent],
  templateUrl: './dotacion.component.html',
  styleUrl: './dotacion.component.scss'
})
export class DotacionComponent implements OnInit {
  viewState = signal<ViewState>('lista');
  gestiones = signal<GestionDotacion[]>([]);
  loading = signal(false);
  showCreateModal = signal(false);
  showPreviewModal = signal(false);
  parsingFile = signal(false);
  importError = signal('');
  gestionNombre = signal('');
  gestionDescripcion = signal('');
  selectedFile = signal<File | null>(null);
  importPreview = signal<VistaPreviaImportacion | null>(null);
  businessPlans = signal<Record<number, Record<string, EmpleadoDotacion[]>>>({});
  realData = signal<Record<number, Record<string, EmpleadoDotacion[]>>>({});
  monthStates = signal<Record<number, Record<string, EstadoMesDotacion>>>({});

  // State for child components
  selectedGestionId = signal<number | null>(null);
  selectedDesde = signal<string | undefined>(undefined);
  selectedHasta = signal<string | undefined>(undefined);
  readonly selectedGestion = computed(() => this.gestiones().find(item => item.id === this.selectedGestionId()) ?? null);
  readonly selectedBusinessPlan = computed(() => this.selectedGestionId() === null ? {} : this.businessPlans()[this.selectedGestionId()!] ?? {});
  readonly selectedRealData = computed(() => this.selectedGestionId() === null ? {} : this.realData()[this.selectedGestionId()!] ?? {});
  readonly selectedMonthStates = computed(() => this.selectedGestionId() === null ? {} : this.monthStates()[this.selectedGestionId()!] ?? {});

  constructor(
    private dotacionService: DotacionService
  ) {}

  ngOnInit(): void {
    this.loadGestiones();
  }

  loadGestiones(): void {
    this.loading.set(true);
    this.dotacionService.getGestiones().subscribe({
      next: (data: GestionDotacion[]) => {
        this.gestiones.set(data);
        this.initializeExistingData(data);
        this.loading.set(false);
      },
      error: (err: any) => {
        console.error('Error cargando gestiones', err);
        this.loading.set(false);
      }
    });
  }

  private initializeExistingData(gestiones: GestionDotacion[]): void {
    const months = this.monthNames();
    const existingRows = this.dotacionService.getEmpleadosMock();
    const plans: Record<number, Record<string, EmpleadoDotacion[]>> = {};
    const actuals: Record<number, Record<string, EmpleadoDotacion[]>> = {};
    const states: Record<number, Record<string, EstadoMesDotacion>> = {};
    for (const gestion of gestiones) {
      plans[gestion.id] = Object.fromEntries(months.map(month => [month, existingRows.map(row => ({ ...row }))]));
      actuals[gestion.id] = {};
      states[gestion.id] = {};
      months.forEach((month, index) => {
        const synced = index < gestion.mesesCargados;
        states[gestion.id][month] = synced ? 'Sincronizado' : 'Datos reales pendientes';
        if (synced) actuals[gestion.id][month] = existingRows.map(row => ({ ...row }));
      });
    }
    this.businessPlans.set(plans);
    this.realData.set(actuals);
    this.monthStates.set(states);
  }

  openCreateModal(): void {
    this.gestionNombre.set('');
    this.gestionDescripcion.set('');
    this.selectedFile.set(null);
    this.importPreview.set(null);
    this.importError.set('');
    this.showCreateModal.set(true);
  }

  closeCreateModal(): void { this.showCreateModal.set(false); }

  async onFileSelected(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;
    input.value = '';
    if (!file) return;
    if (!file.name.toLowerCase().endsWith('.xlsx')) {
      this.importError.set('Selecciona un archivo con formato XLSX.');
      return;
    }
    this.selectedFile.set(file);
    this.importError.set('');
    this.parsingFile.set(true);
    try {
      this.importPreview.set(await parseAnnualBusinessPlan(file));
      this.showCreateModal.set(false);
      this.showPreviewModal.set(true);
    } catch (error) {
      this.importPreview.set(null);
      this.importError.set(error instanceof Error ? error.message : 'No se pudo leer el archivo XLSX.');
    } finally {
      this.parsingFile.set(false);
    }
  }

  cancelPreview(): void {
    this.showPreviewModal.set(false);
    this.showCreateModal.set(true);
  }

  openPreview(): void {
    if (!this.importPreview() || !this.gestionNombre().trim()) return;
    this.showCreateModal.set(false);
    this.showPreviewModal.set(true);
  }

  confirmImport(): void {
    const preview = this.importPreview();
    if (!preview || !this.gestionNombre().trim()) return;
    const id = Math.max(0, ...this.gestiones().map(item => item.id)) + 1;
    const yearMatch = `${this.gestionNombre()} ${this.selectedFile()?.name ?? ''}`.match(/\b(20\d{2})\b/);
    const anio = yearMatch ? Number(yearMatch[1]) : Math.max(new Date().getFullYear(), ...this.gestiones().map(item => item.anio)) + 1;
    const plan = distributeBusinessPlan(preview);
    const gestion: GestionDotacion = {
      id, anio, nombre: this.gestionNombre().trim(), descripcion: this.gestionDescripcion().trim(),
      mesesCargados: 0, totalMeses: 12, tieneDatos: false, tieneBusinessPlan: true,
      comparar: false, sucursal: 'CBB', ciudad: 'Cochabamba'
    };
    this.gestiones.update(items => [...items, gestion]);
    this.businessPlans.update(items => ({ ...items, [id]: plan }));
    this.realData.update(items => ({ ...items, [id]: {} }));
    this.monthStates.update(items => ({
      ...items,
      [id]: Object.fromEntries(this.monthNames().map(month => [month, 'Datos reales pendientes' as EstadoMesDotacion]))
    }));
    this.showPreviewModal.set(false);
    this.onAbrir(gestion);
  }

  onSyncCompleted(event: { month: string; data: EmpleadoDotacion[] }): void {
    const id = this.selectedGestionId();
    if (id === null) return;
    this.realData.update(items => ({ ...items, [id]: { ...(items[id] ?? {}), [event.month]: event.data } }));
    this.monthStates.update(items => ({ ...items, [id]: { ...(items[id] ?? {}), [event.month]: 'Sincronizado' } }));
    this.gestiones.update(items => items.map(item => item.id === id
      ? { ...item, tieneDatos: true, mesesCargados: Object.values(this.monthStates()[id] ?? {}).filter(state => state === 'Sincronizado').length }
      : item));
  }

  private monthNames(): string[] {
    return ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
  }

  onAbrir(gestion: GestionDotacion): void {
    this.selectedGestionId.set(gestion.id);
    this.viewState.set('detalle');
  }

  onViewComplete(data: { id: number, desde?: string, hasta?: string }): void {
    this.selectedGestionId.set(data.id);
    this.selectedDesde.set(data.desde);
    this.selectedHasta.set(data.hasta);
    this.viewState.set('completa');
  }

  goToList(): void {
    this.viewState.set('lista');
    this.selectedGestionId.set(null);
  }

  goToDetalle(): void {
    this.viewState.set('detalle');
  }
}
