export interface PermissionAction {
  key: string;
  label: string;
  colorClass: string;
  activeColorClass: string;
}

export interface PermissionResource {
  name: string;
  permissions: Record<string, boolean | null>; // true: granted, false: denied, null: not applicable
}

export interface PermissionGroup {
  name: string;
  resources: PermissionResource[];
}

export interface AccessTableConfig {
  actions: PermissionAction[];
  groups: PermissionGroup[];
}
