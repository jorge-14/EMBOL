// ─── Row Actions: Usuarios ────────────────────────────────────────────────────
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';

export interface UserRowCallbacks {
  onEdit:       (id: any) => void;
  onDeactivate: (id: any) => void;
  onActivate:   (id: any) => void;
}

export function buildUserRowActions(cb: UserRowCallbacks): DataTableRowAction[] {
  return [
    {
      icon: 'pi pi-pencil',
      tooltip: 'Editar usuario',
      colorClass: 'text-gray-600 hover:text-gray-900',
      handler: (row) => cb.onEdit(row.id),
    },
    {
      icon: 'pi pi-ban',
      tooltip: 'Desactivar',
      colorClass: 'text-red-500 hover:text-red-700',
      handler: (row) => cb.onDeactivate(row.id),
      visible: (row) => row.estado === 'Activo',
    },
    {
      icon: 'pi pi-check-circle',
      tooltip: 'Activar',
      colorClass: 'text-green-500 hover:text-green-700',
      handler: (row) => cb.onActivate(row.id),
      visible: (row) => row.estado === 'Inactivo',
    },
  ];
}
