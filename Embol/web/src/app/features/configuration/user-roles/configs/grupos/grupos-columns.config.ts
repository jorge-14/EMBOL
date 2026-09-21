// ─── Columns: Grupos ─────────────────────────────────────────────────────────
import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { GrupoRow } from '../../models/grupos/grupo.model';

export function buildGruposColumns(): DataTableColumn<GrupoRow>[] {
  return [
    {
      key: 'nombre',
      header: 'Nombre',
      type: 'text',
      minWidth: '200px',
      cssClass: 'font-bold text-gray-900',
    },
    {
      key: 'descripcion',
      header: 'Descripción',
      type: 'text',
      minWidth: '260px',
      cssClass: 'text-gray-500',
    },
    {
      key: 'status',
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
