import { Component, computed, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessTableConfig, PermissionResource, PermissionAction } from './models/access-table.model';

export interface PermissionChange {
  group: string;
  resource: string;
  action: string;
  value: boolean;
}

export interface PropagatePermission extends PermissionChange {
  targets: string[];
}

@Component({
  selector: 'app-access-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './access-table.component.html',
  styleUrl: './access-table.component.css',
})
export class AccessTableComponent {
  readonly pageSize = 10;
  readonly config = input.required<AccessTableConfig>();
  readonly pendingKeys = input<Set<string>>(new Set());
  readonly principals = input<string[]>([]);
  readonly currentPrincipal = input<string>('');
  readonly permissionChange = output<PermissionChange>();
  readonly propagatePermission = output<PropagatePermission>();

  readonly actionPage = signal(0);
  readonly search = signal('');
  readonly expanded = signal<Record<string, boolean>>({});
  readonly selected = signal<Set<string>>(new Set());
  readonly activePopover = signal<string | null>(null);
  readonly propagationTargets = signal<Set<string>>(new Set());
  readonly lastActionPage = computed(() => Math.max(0, Math.ceil(this.config().actions.length / this.pageSize) - 1));
  readonly currentActionPage = computed(() => Math.min(this.actionPage(), this.lastActionPage()));
  readonly visibleActionCount = computed(() => Math.min((this.currentActionPage() + 1) * this.pageSize, this.config().actions.length));
  readonly visibleActions = computed(() => {
    const start = this.currentActionPage() * this.pageSize;
    return this.config().actions.slice(start, start + this.pageSize);
  });
  readonly filteredGroups = computed(() => {
    const query = this.search().trim().toLocaleLowerCase();
    return this.config().groups
      .map(group => ({ ...group, resources: group.resources.filter(resource => resource.name.toLocaleLowerCase().includes(query)) }))
      .filter(group => group.resources.length > 0);
  });
  readonly allExpanded = computed(() => this.filteredGroups().length > 0 && this.filteredGroups().every(group => this.isExpanded(group.name)));

  setSearch(event: Event): void {
    this.clearSelected();
    this.closePopover();
    this.search.set((event.target as HTMLInputElement).value);
  }
  changeActionPage(direction: -1 | 1): void {
    this.actionPage.set(Math.max(0, Math.min(this.lastActionPage(), this.currentActionPage() + direction)));
    this.closePopover();
  }
  isExpanded(name: string): boolean { return this.expanded()[name] !== false; }
  toggleGroup(name: string): void { this.expanded.update(state => ({ ...state, [name]: !this.isExpanded(name) })); }
  toggleAll(): void {
    const expanded = !this.allExpanded();
    this.expanded.update(state => {
      const next = { ...state };
      for (const group of this.filteredGroups()) next[group.name] = expanded;
      return next;
    });
  }
  rowKey(group: string, resource: string): string { return `${group}::${resource}`; }
  cellKey(group: string, resource: string, action: string): string { return `${group}::${resource}::${action}`; }
  isSelected(group: string, resource: string): boolean { return this.selected().has(this.rowKey(group, resource)); }
  toggleSelected(group: string, resource: string): void {
    const next = new Set(this.selected());
    const key = this.rowKey(group, resource);
    if (next.has(key)) next.delete(key); else next.add(key);
    this.selected.set(next);
  }
  clearSelected(): void { this.selected.set(new Set()); }
  togglePermission(group: string, resource: PermissionResource, action: PermissionAction): void {
    const value = resource.permissions[action.key];
    if (value === null) return;
    this.permissionChange.emit({ group, resource: resource.name, action: action.key, value: !value });
  }
  applyBulk(value: boolean): void {
    for (const group of this.config().groups) {
      for (const resource of group.resources) {
        if (!this.isSelected(group.name, resource.name)) continue;
        for (const action of this.visibleActions()) {
          if (resource.permissions[action.key] !== null && resource.permissions[action.key] !== value) {
            this.permissionChange.emit({ group: group.name, resource: resource.name, action: action.key, value });
          }
        }
      }
    }
    this.clearSelected();
  }
  togglePopover(key: string): void {
    this.activePopover.set(this.activePopover() === key ? null : key);
    this.propagationTargets.set(new Set());
  }
  closePopover(): void { this.activePopover.set(null); this.propagationTargets.set(new Set()); }
  toggleTarget(principal: string): void {
    const next = new Set(this.propagationTargets());
    if (next.has(principal)) next.delete(principal); else next.add(principal);
    this.propagationTargets.set(next);
  }
  applyPropagation(group: string, resource: PermissionResource, action: PermissionAction): void {
    const value = resource.permissions[action.key];
    if (value === null || !this.propagationTargets().size) return;
    this.propagatePermission.emit({ group, resource: resource.name, action: action.key, value, targets: [...this.propagationTargets()] });
    this.closePopover();
  }
}
