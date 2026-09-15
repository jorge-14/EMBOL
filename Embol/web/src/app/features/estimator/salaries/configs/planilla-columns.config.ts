import { DataTableColumn } from '../../../../shared/components/data-table/models/data-table.model';
import { PlanillaRow } from '../models/planilla.model';

export function buildPlanillaColumns(periodo: 'Q1' | 'Q2' | 'Q3' | 'Q4' | 'S1' | 'S2' | 'ANUAL' | 'TRIMESTRAL'): DataTableColumn<PlanillaRow>[] {
  const baseCols: DataTableColumn<PlanillaRow>[] = [
    { key: 'nroPers', header: 'N° Pers.', type: 'text', sticky: true, width: '80px' },
    { key: 'nombre', header: 'Nombre', type: 'text', sticky: true, minWidth: '160px', cssClass: '!bg-red-200 font-semibold' },
    { key: 'familia', header: 'Familia', type: 'text', width: '70px', align: 'center' },
    { key: 'cargo', header: 'Cargo', type: 'text', minWidth: '180px' },
    {
      key: 'area', header: 'Área', type: 'badge', width: '70px', align: 'center',
      badgeColorMap: {
        'ADM': 'bg-red-100 text-red-700',
        'PROD': 'bg-green-100 text-green-700',
        'COM': 'bg-blue-100 text-blue-700',
        'FIN': 'bg-purple-100 text-purple-700',
        'MANT': 'bg-teal-100 text-teal-700',
      }
    },
    { key: 'haberBasico', header: 'Haber Básico', type: 'currency', align: 'right', summable: true, editable: true }
  ];

  const allMonths = [
    { key: 'ene', header: 'Ene' }, { key: 'feb', header: 'Feb' }, { key: 'mar', header: 'Mar' },
    { key: 'abr', header: 'Abr' }, { key: 'may', header: 'May' }, { key: 'jun', header: 'Jun' },
    { key: 'jul', header: 'Jul' }, { key: 'ago', header: 'Ago' }, { key: 'sep', header: 'Sep' },
    { key: 'oct', header: 'Oct' }, { key: 'nov', header: 'Nov' }, { key: 'dic', header: 'Dic' }
  ].map(m => ({ ...m, type: 'currency', align: 'right', summable: true, editable: true } as DataTableColumn<PlanillaRow>));

  let monthCols: DataTableColumn<PlanillaRow>[] = [];
  switch (periodo) {
    case 'Q1': monthCols = allMonths.slice(0, 3); break;
    case 'Q2': monthCols = allMonths.slice(3, 6); break;
    case 'Q3': monthCols = allMonths.slice(6, 9); break;
    case 'Q4': monthCols = allMonths.slice(9, 12); break;
    case 'S1': monthCols = allMonths.slice(0, 6); break;
    case 'S2': monthCols = allMonths.slice(6, 12); break;
    case 'TRIMESTRAL':
      monthCols = [
        { key: 'q1' as any, header: 'Q1 (Ene-Mar)', type: 'currency', align: 'right', summable: true },
        { key: 'q2' as any, header: 'Q2 (Abr-Jun)', type: 'currency', align: 'right', summable: true },
        { key: 'q3' as any, header: 'Q3 (Jul-Sep)', type: 'currency', align: 'right', summable: true },
        { key: 'q4' as any, header: 'Q4 (Oct-Dic)', type: 'currency', align: 'right', summable: true },
      ];
      break;
    case 'ANUAL': default: monthCols = allMonths; break;
  }

  const endCols: DataTableColumn<PlanillaRow>[] = [
    { key: 'totalAnual', header: 'Total Anual', type: 'currency', align: 'right', summable: true, cssClass: 'font-bold text-blue-700' },
    { key: 'cargasPorcentaje', header: 'Cargas (43.2%)', type: 'currency', align: 'right', summable: true, cssClass: 'text-brand' },
  ];

  return [...baseCols, ...monthCols, ...endCols];
}
