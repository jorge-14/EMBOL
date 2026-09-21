import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccessTableComponent } from '../../../../../shared/components/access-table/access-table.component';
import { AccessTableConfig } from '../../../../../shared/components/access-table/models/access-table.model';
import { AccessSummaryModalComponent } from '../../../../../shared/components/access-summary-modal/access-summary-modal.component';

@Component({
  selector: 'app-access-list',
  standalone: true,
  imports: [CommonModule, FormsModule, AccessTableComponent, AccessSummaryModalComponent],
  templateUrl: './access-list.component.html',
})
export class AccessListComponent {
  mode = signal<'rol' | 'grupo'>('rol');
  selectedRole = signal<string>('Finanzas');
  isSummaryOpen = signal<boolean>(false);

  roles = signal<string[]>(['Finanzas', 'Administrador', 'Jefe de Planta', 'Consultor']);

  tableConfig = signal<AccessTableConfig>({
    actions: [
      { key: 'ver',       label: 'VER',       colorClass: 'text-green-600',   activeColorClass: 'bg-green-500 border-green-600' },
      { key: 'crear',     label: 'CREAR',     colorClass: 'text-blue-600',    activeColorClass: 'bg-blue-500 border-blue-600' },
      { key: 'modificar', label: 'MODIFICAR', colorClass: 'text-amber-500',   activeColorClass: 'bg-amber-500 border-amber-600' },
      { key: 'eliminar',  label: 'ELIMINAR',  colorClass: 'text-red-600',    activeColorClass: 'bg-red-500 border-red-600' },
      { key: 'descargar', label: 'DESCARGAR', colorClass: 'text-purple-600', activeColorClass: 'bg-purple-500 border-purple-600' },
      { key: 'exportar',  label: 'EXPORTAR',  colorClass: 'text-emerald-500',activeColorClass: 'bg-emerald-500 border-emerald-600' },
      { key: 'aprobar',   label: 'APROBAR',   colorClass: 'text-rose-500',   activeColorClass: 'bg-rose-500 border-rose-600' },
    ],
    groups: [
      {
        name: 'ADMINISTRACIÓN',
        resources: [
          {
            name: 'Usuarios',
            permissions: { ver: true, crear: true, modificar: false, eliminar: true, descargar: null, exportar: null, aprobar: null }
          },
          {
            name: 'Roles y Grupos',
            permissions: { ver: true, crear: true, modificar: true, eliminar: true, descargar: null, exportar: null, aprobar: null }
          },
          {
            name: 'Parámetros del Sistema',
            permissions: { ver: true, crear: null, modificar: true, eliminar: null, descargar: null, exportar: null, aprobar: true }
          },
        ]
      },
      {
        name: 'SIMULADOR',
        resources: [
          {
            name: 'Simulador Salarial',
            permissions: { ver: true, crear: true, modificar: true, eliminar: false, descargar: null, exportar: true, aprobar: true }
          },
          {
            name: 'Escenarios',
            permissions: { ver: true, crear: true, modificar: true, eliminar: true, descargar: null, exportar: null, aprobar: true }
          },
          {
            name: 'Reportes Simulador',
            permissions: { ver: true, crear: null, modificar: null, eliminar: null, descargar: true, exportar: true, aprobar: null }
          },
        ]
      }
    ]
  });

  setMode(newMode: 'rol' | 'grupo'): void {
    this.mode.set(newMode);
  }

  toggleSummary(): void {
    this.isSummaryOpen.update(prev => !prev);
  }

  onPermissionChange(event: any): void {
    console.log('Permission changed:', event);
  }

  saveChanges(): void {
    console.log('Saving changes...', this.tableConfig().groups);
  }
}
