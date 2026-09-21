import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DotacionService } from '../../../../../core/services/dotacion.service';
import { GestionDotacion } from '../../models/dotacion.model';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';
import { GestionDetalleComponent } from '../../components/gestion-detalle/gestion-detalle.component';
import { VistaCompletaComponent } from '../../components/vista-completa/vista-completa.component';

type ViewState = 'lista' | 'detalle' | 'completa';

@Component({
  selector: 'app-dotacion',
  standalone: true,
  imports: [CommonModule, CheckboxModule, FormsModule, GestionDetalleComponent, VistaCompletaComponent],
  templateUrl: './dotacion.component.html',
  styleUrl: './dotacion.component.scss'
})
export class DotacionComponent implements OnInit {
  viewState = signal<ViewState>('lista');
  gestiones = signal<GestionDotacion[]>([]);
  loading = signal(false);

  // State for child components
  selectedGestionId = signal<number | null>(null);
  selectedDesde = signal<string | undefined>(undefined);
  selectedHasta = signal<string | undefined>(undefined);

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
        this.loading.set(false);
      },
      error: (err: any) => {
        console.error('Error cargando gestiones', err);
        this.loading.set(false);
      }
    });
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
