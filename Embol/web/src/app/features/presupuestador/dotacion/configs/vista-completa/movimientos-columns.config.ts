import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { MovimientoPlanificado } from '../../models/dotacion.model';

export const MOVIMIENTOS_COLUMNS: DataTableColumn<MovimientoPlanificado>[] = [
  {
    key: 'tipo',
    header: 'Tipo',
    type: 'badge',
    badgeColorMap: {
      'Adición': 'bg-green-100 text-green-700 border-green-200',
      'Desvinculación': 'bg-red-100 text-red-700 border-red-200',
      'Desfase': 'bg-orange-100 text-orange-700 border-orange-200',
      'Vacante': 'bg-purple-100 text-purple-700 border-purple-200'
    }
  },
  { key: 'nombre', header: 'Nombre', type: 'text', sticky: true, width: '200px' },
  { key: 'cargo', header: 'Cargo', type: 'text' },
  { key: 'area', header: 'Área', type: 'text' },
  { key: 'planta', header: 'Planta', type: 'text' },
  { key: 'observaciones', header: 'Observaciones', type: 'text' }
];
