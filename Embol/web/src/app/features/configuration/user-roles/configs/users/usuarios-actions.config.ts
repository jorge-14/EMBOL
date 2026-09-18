// ─── Row Actions: Usuarios ────────────────────────────────────────────────────
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';

export interface UserRowCallbacks {
  onEdit:       (id: any) => void;
  onDelete:     (id: any) => void;
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
      icon: 'pi pi-trash',
      tooltip: 'Eliminar Usuario',
      colorClass: 'text-red-500 hover:text-red-700',
      handler: (row) => cb.onDelete(row.id),
    },
  ];
}
