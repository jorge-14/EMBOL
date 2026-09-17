// ─── Data Table Configuration Models ───
// Interfaces for the reusable DataTable component
import { PageMetadata } from '../../../models/pagination.model';

export interface DataTableColumn<T = any> {
  key: string;
  header: string;
  type: 'text' | 'number' | 'currency' | 'badge';
  editable?: boolean;
  width?: string;
  minWidth?: string;
  sticky?: boolean;
  summable?: boolean;
  align?: 'left' | 'center' | 'right';
  formatter?: (value: any, row: T) => string;
  badgeColorMap?: Record<string, string>;
  cssClass?: string;
}

export interface DataTableTab {
  id: string;
  label: string;
  active?: boolean;
  colorClass?: string;
}

export interface DataTableSummaryCard {
  label: string;
  value: string;
  borderColor: string;
}

export interface DataTableRowAction<T = any> {
  icon: string;
  tooltip: string;
  colorClass?: string;
  handler: (row: T, index: number) => void;
  visible?: (row: T) => boolean;
}

export interface DataTableGlobalAction {
  label: string;
  colorClass: string;
  handler: () => void;
  disabled?: boolean;
}

export interface DataTableCollapsibleSection {
  title: string;
  columns: DataTableColumn[];
  data: any[];
  expanded: boolean;
}

export interface DataTableConfig<T = any> {
  tabs?: DataTableTab[];
  summaryCards?: DataTableSummaryCard[];
  columns: DataTableColumn<T>[];
  rowActions?: DataTableRowAction<T>[];
  globalActions?: DataTableGlobalAction[];
  collapsibleSections?: DataTableCollapsibleSection[];
  showTotalsRow?: boolean;
  totalsLabel?: string;
  recordCount?: number;
  lastUpdated?: string;
  trackByKey?: string;
  editableMode?: boolean;
}

export interface CellChangeEvent<T = any> {
  row: T;
  rowIndex: number;
  column: string;
  oldValue: any;
  newValue: any;
}

export interface DirtyCellKey {
  rowId: string | number;
  column: string;
}

