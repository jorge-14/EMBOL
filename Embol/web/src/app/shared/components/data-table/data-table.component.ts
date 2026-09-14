import { Component, input, output, signal, computed } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  DataTableColumn,
  DataTableTab,
  DataTableSummaryCard,
  DataTableRowAction,
  DataTableGlobalAction,
  DataTableCollapsibleSection,
  CellChangeEvent,
} from './models/data-table.model';
import { PageMetadata } from '../../models/pagination.model';
@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './data-table.component.html',
})
export class DataTableComponent {

  private searchSubject = new Subject<string>();

  constructor() {
    this.searchSubject.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      takeUntilDestroyed()
    ).subscribe(value => {
      this.filterChange.emit(value);
    });
  }

  // ── Inputs ──
  data            = input.required<any[]>();
  columns         = input.required<DataTableColumn[]>();
  tabs            = input<DataTableTab[]>([]);
  summaryCards    = input<DataTableSummaryCard[]>([]);
  rowActions      = input<DataTableRowAction[]>([]);
  globalActions   = input<DataTableGlobalAction[]>([]);
  collapsibleSections = input<DataTableCollapsibleSection[]>([]);
  showTotalsRow   = input<boolean>(false);
  totalsLabel     = input<string>('TOTAL');
  recordCount     = input<number>(0);
  lastUpdated     = input<string>('');
  trackByKey      = input<string>('id');
  editableMode    = input<boolean>(false);
  showPagination  = input<boolean>(false);
  pageInfo        = input<PageMetadata | null>(null);

  showFilter      = input<boolean>(false);
  showCustomFilters = input<boolean>(false);
  headerBgClass   = input<string>('bg-table-accent');
  headerTextClass = input<string>('text-gray-900');

  totalsBgClass   = input<string>('bg-table-accent');
  totalsTextClass = input<string>('text-gray-900');

  footerBgClass   = input<string>('bg-gray-100');
  footerTextClass = input<string>('text-gray-700');

  // ── Outputs ──
  tabChange       = output<DataTableTab>();
  cellChange      = output<CellChangeEvent>();
  rowActionClick  = output<{ action: DataTableRowAction; row: any; index: number }>();
  pageChange      = output<number>();
  pageSizeChange  = output<number>();
  filterChange    = output<string>();

  // ── Internal State ──
  editingCell     = signal<{ rowIndex: number; column: string } | null>(null);
  editValue       = signal<string>('');
  dirtyCells      = signal<Map<string, Set<string>>>(new Map());
  filterText      = signal<string>('');

  // ── Computed ──
  filteredData = computed(() => {
    const text = this.filterText().toLowerCase().trim();
    const currentData = this.data();
    if (!text) return currentData;

    return currentData.filter(row => {
      return Object.values(row).some(val =>
        String(val).toLowerCase().includes(text)
      );
    });
  });

  enrichedColumns = computed(() => {
    let offset = 0;
    return this.columns().map(c => {
      const leftOffset = c.sticky ? `${offset}px` : 'auto';
      if (c.sticky) {
        const wStr = c.width || c.minWidth || '0px';
        const wVal = parseInt(wStr.replace(/[^0-9]/g, ''), 10) || 0;
        offset += wVal;
      }
      return { ...c, leftOffset };
    });
  });

  summableColumns = computed(() =>
    this.columns().filter(c => c.summable)
  );

  nonSummableLeadColumns = computed(() =>
    this.columns().filter(c => !c.summable)
  );

  // ── Template helpers ──
  Math = Math;

  toStr(value: any): string {
    return String(value);
  }

  // ── Pagination ──
  onPageChange(page: number): void {
    const info = this.pageInfo();
    if (info && page >= 0 && page < info.totalPages) {
      this.pageChange.emit(page);
    }
  }

  onPageSizeChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const size = parseInt(select.value, 10);
    if (!isNaN(size)) {
      this.pageSizeChange.emit(size);
    }
  }

  getShowingFrom(): number {
    const info = this.pageInfo();
    if (!info || info.totalElements === 0) return 0;
    return (info.number * info.size) + 1;
  }

  getShowingTo(): number {
    const info = this.pageInfo();
    if (!info) return 0;
    return Math.min((info.number + 1) * info.size, info.totalElements);
  }

  // ── Tab handling ──
  onTabClick(tab: DataTableTab): void {
    this.tabChange.emit(tab);
  }

  // ── Filter handling ──
  onFilterChange(value: string): void {
    this.filterText.set(value);
    this.searchSubject.next(value);
  }

  // ── Cell editing ──
  startEdit(rowIndex: number, column: string): void {
    if (!this.editableMode()) return;
    const col = this.columns().find(c => c.key === column);
    if (!col?.editable) return;

    const row = this.filteredData()[rowIndex];
    this.editingCell.set({ rowIndex, column });
    this.editValue.set(String(row[column] ?? ''));
  }

  isEditing(rowIndex: number, column: string): boolean {
    const cell = this.editingCell();
    return cell !== null && cell.rowIndex === rowIndex && cell.column === column;
  }

  onCellBlur(row: any, rowIndex: number, col: DataTableColumn, event: Event): void {
    const cell = this.editingCell();
    if (!cell) return;

    const input = event.target as HTMLInputElement;
    const newValue = col.type === 'number' || col.type === 'currency'
      ? parseFloat(input.value) || 0
      : input.value;

    const oldValue = row[col.key];

    if (newValue !== oldValue) {
      // Mark as dirty
      const dirty = new Map(this.dirtyCells());
      const rowKey = String(row[this.trackByKey()]);
      if (!dirty.has(rowKey)) {
        dirty.set(rowKey, new Set());
      }
      dirty.get(rowKey)!.add(col.key);
      this.dirtyCells.set(dirty);

      // Emit change
      this.cellChange.emit({
        row,
        rowIndex,
        column: col.key,
        oldValue,
        newValue
      });
    }

    this.editingCell.set(null);
  }

  onCellKeydown(row: any, rowIndex: number, col: DataTableColumn, event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      (event.target as HTMLInputElement).blur();
    } else if (event.key === 'Escape') {
      this.editingCell.set(null);
    }
  }

  isCellDirty(row: any, column: string): boolean {
    const rowKey = String(row[this.trackByKey()]);
    return this.dirtyCells().get(rowKey)?.has(column) ?? false;
  }

  // ── Clear dirty state (after save) ──
  clearDirtyState(): void {
    this.dirtyCells.set(new Map());
  }


  // ── Formatting ──
  formatCellValue(row: any, col: DataTableColumn): string {
    const value = row[col.key];
    if (col.formatter) {
      return col.formatter(value, row);
    }
    if (col.type === 'currency') {
      return this.formatCurrency(value);
    }
    if (col.type === 'number') {
      return this.formatNumber(value);
    }
    return value ?? '';
  }

  formatCurrency(value: number | null | undefined): string {
    if (value === null || value === undefined) return '';
    const intPart = Math.round(value).toString();
    const formatted = intPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    return `Bs ${formatted}`;
  }

  formatNumber(value: number | null | undefined): string {
    if (value === null || value === undefined) return '';
    return Math.round(value).toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
  }

  // ── Totals ──
  calculateTotal(columnKey: string): number {
    return this.filteredData().reduce((sum, row) => sum + (Number(row[columnKey]) || 0), 0);
  }


  // ── Collapsible sections ──
  toggleSection(section: DataTableCollapsibleSection): void {
    section.expanded = !section.expanded;
  }

  // ── Badge color ──
  getBadgeClasses(col: DataTableColumn, value: string): string {
    if (col.badgeColorMap && col.badgeColorMap[value]) {
      return col.badgeColorMap[value];
    }
    return 'bg-gray-100 text-gray-800';
  }

  // ── Row action handling ──
  onRowAction(action: DataTableRowAction, row: any, index: number): void {
    action.handler(row, index);
    this.rowActionClick.emit({ action, row, index });
  }

  isActionVisible(action: DataTableRowAction, row: any): boolean {
    return action.visible ? action.visible(row) : true;
  }

  // ── Tracking ──
  trackByFn(index: number, item: any): any {
    return item[this.trackByKey()] ?? index;
  }
}
