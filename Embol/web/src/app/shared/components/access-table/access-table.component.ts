import { Component, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessTableConfig, PermissionResource, PermissionAction } from './models/access-table.model';

@Component({
  selector: 'app-access-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './access-table.component.html',
})
export class AccessTableComponent {
  config = input.required<AccessTableConfig>();

  permissionChange = output<{
    resource: string;
    action: string;
    value: boolean;
  }>();

  togglePermission(resource: PermissionResource, action: PermissionAction): void {
    const currentValue = resource.permissions[action.key];
    if (currentValue === null) return;

    const newValue = !currentValue;
    resource.permissions[action.key] = newValue;

    this.permissionChange.emit({
      resource: resource.name,
      action: action.key,
      value: newValue
    });
  }
}
