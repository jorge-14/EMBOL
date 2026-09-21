import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DotacionService } from '../../services/dotacion.service';
import { GestionDetalle } from '../../models/dotacion.model';
import { DialogModule } from 'primeng/dialog';
import { HorizontalSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestion-detalle',
  standalone: true,
  imports: [CommonModule, DialogModule, HorizontalSelectComponent, FormsModule],
  templateUrl: './gestion-detalle.component.html',
  styleUrl: './gestion-detalle.component.scss'
})
export class GestionDetalleComponent implements OnInit {
  detalle = signal<GestionDetalle | null>(null);
  loading = signal(false);

  // Rango Modal
  showRangeModal = signal(false);
  mesesOptions = [
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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dotacionService: DotacionService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.loadDetalle(id);
    }
  }

  loadDetalle(id: number): void {
    this.loading.set(true);
    this.dotacionService.getGestionById(id).subscribe({
      next: (data) => {
        if (data) {
          this.detalle.set(data);
        }
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error cargando detalle', err);
        this.loading.set(false);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/presupuestador/dotacion']);
  }

  openRangeModal(): void {
    this.showRangeModal.set(true);
  }

  closeRangeModal(): void {
    this.showRangeModal.set(false);
  }

  verConsolidado(): void {
    if (this.detalle()) {
      this.router.navigate(['/presupuestador/dotacion', this.detalle()?.id, 'vista-completa'], {
        queryParams: {
          desde: this.desdeMes(),
          hasta: this.hastaMes()
        }
      });
      this.closeRangeModal();
    }
  }

  verVistaCompleta(): void {
    if (this.detalle()) {
      this.router.navigate(['/presupuestador/dotacion', this.detalle()?.id, 'vista-completa']);
    }
  }
}
