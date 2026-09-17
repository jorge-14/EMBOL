// ─── Columns: Roles ──────────────────────────────────────────────────────────
import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { RolRow } from '../../models/roles/rol.model';

export function buildRolesColumns(): DataTableColumn<RolRow>[] {
  return [
    {
      key: 'nombre',
      header: 'Nombre',
      type: 'text',
      minWidth: '180px',
      cssClass: 'font-bold text-gray-900',
    },
    {
      key: 'descripcion',
      header: 'Descripción',
      type: 'text',
      minWidth: '240px',
      cssClass: 'text-gray-500',
    },
    {
      key: 'estado',
      header: 'Estado',
      type: 'badge',
      width: '100px',
      align: 'center',
      badgeColorMap: {
        'Activo':   'bg-green-50 text-green-700 border border-green-100',
        'Inactivo': 'bg-gray-50  text-gray-500  border border-gray-100',
      },
    },
  ];
}
