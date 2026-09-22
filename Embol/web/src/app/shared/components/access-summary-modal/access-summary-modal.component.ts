import { Component, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessTableConfig } from '../access-table/models/access-table.model';

@Component({
  selector: 'app-access-summary-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './access-summary-modal.component.html',
})
export class AccessSummaryModalComponent {
  isOpen = input.required<boolean>();
  title = input<string>('Resumen de permisos');
  subtitle = input<string>('Vista informativa');
  config = input.required<AccessTableConfig>();

  close = output<void>();

  // Track expanded groups
  expandedGroups = signal<Record<string, boolean>>({});

  ngOnInit() {
    // Expand all groups by default
    const initialExpanded: Record<string, boolean> = {};
    this.config().groups.forEach(group => {
      initialExpanded[group.name] = true;
    });
    this.expandedGroups.set(initialExpanded);
  }

  toggleGroup(groupName: string): void {
    const current = this.expandedGroups();
    this.expandedGroups.set({
      ...current,
      [groupName]: !current[groupName]
    });
  }

  onClose(): void {
    this.close.emit();
  }

  getPermissionClass(key: string): string {
    return 'bg-[#E8FBE7] text-[#267124] border-[#A8E8A5]';
  }

  getDotClass(key: string): string {
    return 'bg-[#06B900]';
  }
}
