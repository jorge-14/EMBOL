import { DataTableGlobalAction } from '../../../../shared/components/data-table/models/data-table.model';

export interface PlanillaActionCallbacks {
  onAddPersonal: () => void;
  onOpenSettings: () => void;
  onSave: () => void;
  onExport: () => void;
  onRecalculate: () => void;
}

export function buildPlanillaGlobalActions(callbacks: PlanillaActionCallbacks): DataTableGlobalAction[] {
  return [
    { label: 'Personal', colorClass: 'btn-primary', handler: callbacks.onAddPersonal },
    { label: 'Ajustes', colorClass: 'btn-primary', handler: callbacks.onOpenSettings },
    { label: 'Guardar', colorClass: 'btn-primary', handler: callbacks.onSave },
    { label: 'Exportar', colorClass: 'btn-primary', handler: callbacks.onExport },
    { label: 'Recalcular', colorClass: 'btn-primary', handler: callbacks.onRecalculate },
  ];
}
