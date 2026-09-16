import { DataTableCollapsibleSection } from "../../../../shared/components/data-table/models/data-table.model";
import { CargaSocial } from '../models/planilla.model';

export function buildPlanillaCollapsibles(cargasSociales: CargaSocial[]): DataTableCollapsibleSection[] {
  return [
    {
      title: 'Desglose de Cargas Sociales y Provisiones',
      expanded: false,
      columns: [
        { key: 'concepto',     header: 'Concepto',      type: 'text' },
        { key: 'porcentaje',   header: 'Porcentaje',    type: 'number', align: 'left', cssClass: 'text-brand font-bold' },
        { key: 'base',         header: 'Base',          type: 'text' },
        { key: 'montoMensual', header: 'Monto Mensual', type: 'currency', align: 'right' },
        { key: 'montoAnual',   header: 'Monto Anual',   type: 'currency', align: 'right' },
      ],
      data: cargasSociales,
    }
  ];
}
