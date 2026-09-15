import { DataTableSummaryCard } from "../../../../shared/components/data-table/models/data-table.model";
  import { PlanillaSummary } from '../models/planilla.model';

export function buildPlanillaSummaryCards(
  summary: PlanillaSummary | null,
  formatFn: (value: number) => string
): DataTableSummaryCard[] {
  if (!summary) return [];
  
  return [
    { label: 'Total Planilla Anual',  value: formatFn(summary.totalPlanillaAnual),  borderColor: '#f59e0b' },
    { label: 'Total Cargas Sociales', value: formatFn(summary.totalCargasSociales), borderColor: '#ef4444' },
    { label: 'Total Provisiones',     value: formatFn(summary.totalProvisiones),     borderColor: '#f97316' },
    { label: 'Gran Total CMO',        value: formatFn(summary.granTotalCMO),         borderColor: '#22c55e' },
  ];
}
