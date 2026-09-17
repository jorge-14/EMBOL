// ─── Row Actions: Roles ───────────────────────────────────────────────────────
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';

export interface RolRowCallbacks {
  onEdit:   (id: any) => void;
  onDelete: (id: any) => void;
}

export function buildRolRowActions(cb: RolRowCallbacks): DataTableRowAction[] {
  return [
    {
      icon: 'pi pi-pencil',
      tooltip: 'Editar rol',
      colorClass: 'text-gray-600 hover:text-gray-900',
      handler: (row) => cb.onEdit(row.id),
    },
    {
      icon: 'pi pi-trash',
      tooltip: 'Eliminar rol',
      colorClass: 'text-red-500 hover:text-red-700',
      handler: (row) => cb.onDelete(row.id),
    },
  ];
}
