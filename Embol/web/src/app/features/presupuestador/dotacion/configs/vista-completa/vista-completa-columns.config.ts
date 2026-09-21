import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { EmpleadoDotacion } from '../../models/dotacion.model';

export const VISTA_COMPLETA_COLUMNS: DataTableColumn<EmpleadoDotacion>[] = [
  { key: 'nombre', header: 'Nombre', type: 'text', sticky: true, width: '200px' },
  { key: 'ci', header: 'CI', type: 'text' },
  { key: 'tipo', header: 'Tipo', type: 'text' },
  { key: 'categoria', header: 'Categoría', type: 'text' },
  { key: 'centroCosto', header: 'Centro costo', type: 'text' },
  { key: 'cargo', header: 'Cargo', type: 'text' },
  { key: 'area', header: 'Área', type: 'text' },
  { key: 'fechaIngreso', header: 'Fecha ingreso', type: 'text' },
  { key: 'correo', header: 'Correo', type: 'text' },
  { key: 'direccion', header: 'Dirección', type: 'text' },
  { key: 'telefono', header: 'Teléfono', type: 'text' },
  { key: 'seguro', header: 'Seguro', type: 'text' },
  { key: 'contactoEmerg', header: 'Contacto emerg.', type: 'text' },
  { key: 'nua', header: 'NUA', type: 'text' },
  { key: 'afp', header: 'AFP', type: 'text' },
  { key: 'planta', header: 'Planta', type: 'text' },
  { key: 'turno', header: 'Turno', type: 'text' },
  { key: 'jornada', header: 'Jornada', type: 'text' },
  { key: 'banco', header: 'Banco', type: 'text' },
  { key: 'cuentaBancaria', header: 'Cuenta bancaria', type: 'text' },
  { key: 'salarioBase', header: 'Salario base', type: 'currency' },
  { key: 'bonoAntiguedad', header: 'Bono antigüedad', type: 'currency' },
  { key: 'bonoProduccion', header: 'Bono producción', type: 'currency' },
  { key: 'estado', header: 'Estado', type: 'text' },
  { key: 'fechaBaja', header: 'Fecha baja', type: 'text' },
  { key: 'motivoBaja', header: 'Motivo baja', type: 'text' },
  { key: 'familiaCargo', header: 'Familia a cargo', type: 'number' },
  {
    key: 'movimiento',
    header: 'Movimiento',
    type: 'badge',
    badgeColorMap: {
      'Adición': 'bg-green-100 text-green-700 border-green-200',
      'Desvinculación': 'bg-red-100 text-red-700 border-red-200',
      'Ninguno': 'hidden'
    }
  }
];
