import { Component, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessTableConfig } from '../access-table/models/access-table.model';

@Component({
  selector: 'app-access-summary-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './access-summary-modal.component.html',
})
export class AccessSummaryModalComponent {
  isOpen = input.required<boolean>();
  title = input<string>('Resumen de permisos');
  subtitle = input<string>('Vista informativa');
  config = input.required<AccessTableConfig>();

  close = output<void>();

  // Track expanded groups
  expandedGroups = signal<Record<string, boolean>>({});

  ngOnInit() {
    // Expand all groups by default
    const initialExpanded: Record<string, boolean> = {};
    this.config().groups.forEach(group => {
      initialExpanded[group.name] = true;
    });
    this.expandedGroups.set(initialExpanded);
  }

  toggleGroup(groupName: string): void {
    const current = this.expandedGroups();
    this.expandedGroups.set({
      ...current,
      [groupName]: !current[groupName]
    });
  }

  onClose(): void {
    this.close.emit();
  }

  getPermissionClass(key: string): string {
    switch (key) {
      case 'ver': return 'bg-green-50 text-green-700 border-green-200';
      case 'crear': return 'bg-blue-50 text-blue-700 border-blue-200';
      case 'modificar': return 'bg-amber-50 text-amber-700 border-amber-200';
      case 'eliminar': return 'bg-red-50 text-red-700 border-red-200';
      case 'descargar': return 'bg-purple-50 text-purple-700 border-purple-200';
      case 'exportar': return 'bg-emerald-50 text-emerald-700 border-emerald-200';
      case 'aprobar': return 'bg-rose-50 text-rose-700 border-rose-200';
      default: return 'bg-gray-50 text-gray-700 border-gray-200';
    }
  }

  getDotClass(key: string): string {
    switch (key) {
      case 'ver': return 'bg-green-500';
      case 'crear': return 'bg-blue-500';
      case 'modificar': return 'bg-amber-500';
      case 'eliminar': return 'bg-red-500';
      case 'descargar': return 'bg-purple-500';
      case 'exportar': return 'bg-emerald-500';
      case 'aprobar': return 'bg-rose-500';
      default: return 'bg-gray-500';
    }
  }
}
