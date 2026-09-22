import { Component, computed, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmpleadoDotacion, EstadoMesDotacion, GestionDetalle, GestionDotacion } from '../../models/dotacion.model';
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
export class GestionDetalleComponent {
  readonly gestion = input.required<GestionDotacion>();
  readonly businessPlanByMonth = input<Record<string, EmpleadoDotacion[]>>({});
  readonly monthStates = input<Record<string, EstadoMesDotacion>>({});

  back = output<void>();
  viewComplete = output<{ id: number, desde?: string, hasta?: string }>();

  readonly detalle = computed<GestionDetalle>(() => {
    const gestion = this.gestion();
    return {
      id: gestion.id, anio: gestion.anio, sucursal: gestion.sucursal ?? '', ciudad: gestion.ciudad ?? '',
      mesesCargados: Object.values(this.monthStates()).filter(state => state === 'Sincronizado').length,
      totalMeses: 12,
      meses: this.mesesOptions.map(option => {
        const state = this.monthStates()[option.value] ?? 'BP disponible';
        return {
          nombre: option.value,
          empleados: this.businessPlanByMonth()[option.value]?.length ?? 0,
          disponible: true,
          cargado: state === 'Sincronizado',
          estado: state
        };
      })
    };
  });

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
    if (this.detalle()) {
      this.viewComplete.emit({
        id: this.detalle().id,
        desde: this.desdeMes(),
        hasta: this.hastaMes()
      });
      this.closeRangeModal();
    }
  }

  verVistaCompleta(mes?: string): void {
    if (this.detalle()) {
      this.viewComplete.emit({ 
        id: this.detalle().id,
        desde: mes,
        hasta: mes
      });
    }
  }
}
