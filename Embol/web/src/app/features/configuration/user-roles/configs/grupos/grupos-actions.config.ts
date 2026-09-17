// ─── Row Actions: Grupos ──────────────────────────────────────────────────────
import { DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';

export interface GrupoRowCallbacks {
  onEdit:   (id: any) => void;
  onDelete: (id: any) => void;
}

export function buildGrupoRowActions(cb: GrupoRowCallbacks): DataTableRowAction[] {
  return [
    {
      icon: 'pi pi-pencil',
      tooltip: 'Editar grupo',
      colorClass: 'text-gray-600 hover:text-gray-900',
      handler: (row) => cb.onEdit(row.id),
    },
    {
      icon: 'pi pi-trash',
      tooltip: 'Eliminar grupo',
      colorClass: 'text-red-500 hover:text-red-700',
      handler: (row) => cb.onDelete(row.id),
    },
  ];
}
