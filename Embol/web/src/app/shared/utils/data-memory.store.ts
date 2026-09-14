import { Injectable, signal, computed } from '@angular/core';

/**
 * Store en memoria genérico para edición batch (edición en memoria).
 *
 * Patrón:
 *  1. loadData() → recibe datos del backend, crea snapshot original + copia de trabajo
 *  2. updateCell() → modifica la copia de trabajo y trackea el cambio
 *  3. getChangesPayload() → devuelve solo las filas modificadas para enviar al backend
 *  4. revertAll() → restaura los datos originales
 *
 * Cada feature (Planilla, Usuarios, etc) puede instanciar su propio store
 * localmente en los providers del componente: providers: [DataMemoryStore]
 */
@Injectable()
export class DataMemoryStore<T extends Record<string, any> & { id: string | number }> {

  // ── State ──
  private _originalData = signal<T[]>([]);
  private _workingData  = signal<T[]>([]);
  private _changes      = signal<Map<string | number, Map<string, { original: any; current: any }>>>(new Map());

  // ── Computed ──
  readonly data         = this._workingData.asReadonly();
  readonly hasChanges   = computed(() => this._changes().size > 0);
  readonly changeCount  = computed(() => this._changes().size);
  readonly dirtyRowIds  = computed(() => [...this._changes().keys()]);

  // ── Load data from backend ──
  loadData(data: T[]): void {
    const cloned = structuredClone(data);
    this._originalData.set(cloned);
    this._workingData.set(structuredClone(data));
    this._changes.set(new Map());
  }

  // ── Update a single cell ──
  updateCell(rowId: string | number, key: string, value: any): void {
    const data = this._workingData().map(row => {
      if (row['id'] === rowId) {
        return { ...row, [key]: value };
      }
      return row;
    });
    this._workingData.set(data);

    // Track change
    const changes = new Map(this._changes());
    if (!changes.has(rowId)) {
      changes.set(rowId, new Map());
    }

    const original = this._originalData().find(r => r['id'] === rowId);
    const originalValue = original ? original[key] : undefined;

    const cellChanges = changes.get(rowId)!;
    if (value === originalValue) {
      // Reverted to original — remove from dirty
      cellChanges.delete(key);
      if (cellChanges.size === 0) {
        changes.delete(rowId);
      }
    } else {
      cellChanges.set(key, { original: originalValue, current: value });
    }

    this._changes.set(changes);
  }

  // ── Check if a specific cell is dirty ──
  isCellDirty(rowId: string | number, column: string): boolean {
    const rowChanges = this._changes().get(rowId);
    return rowChanges ? rowChanges.has(column) : false;
  }

  // ── Check if a row has any dirty cells ──
  isRowDirty(rowId: string | number): boolean {
    return this._changes().has(rowId);
  }

  // ── Revert a single row to original ──
  revertRow(rowId: string | number): void {
    const original = this._originalData().find(r => r['id'] === rowId);
    if (!original) return;

    const data = this._workingData().map(row =>
      row['id'] === rowId ? structuredClone(original) : row
    );
    this._workingData.set(data);

    const changes = new Map(this._changes());
    changes.delete(rowId);
    this._changes.set(changes);
  }

  // ── Revert all changes ──
  revertAll(): void {
    this._workingData.set(structuredClone(this._originalData()));
    this._changes.set(new Map());
  }

  // ── Get payload of only changed rows for backend push ──
  getChangesPayload(): { id: string | number; changes: Record<string, any> }[] {
    const result: { id: string | number; changes: Record<string, any> }[] = [];

    this._changes().forEach((cellChanges, rowId) => {
      const changesObj: Record<string, any> = {};
      cellChanges.forEach((val, key) => {
        changesObj[key] = val.current;
      });
      result.push({ id: rowId, changes: changesObj });
    });

    return result;
  }

  // ── Get full changed rows (complete objects, not just diffs) ──
  getChangedRows(): T[] {
    const dirtyIds = this.dirtyRowIds();
    return this._workingData().filter(row => dirtyIds.includes(row['id']));
  }

  // ── After successful backend save, update the original snapshot ──
  commitChanges(): void {
    this._originalData.set(structuredClone(this._workingData()));
    this._changes.set(new Map());
  }
}
