// ─── UserRoles Feature Imports ────────────────────────────────────────────────

import { CommonModule } from '@angular/common';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table.component';
import { HorizontalSelectComponent } from '../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { DynamicFormComponent } from '../../../../shared/components/dynamic-form/dynamic-form.component';

export const USUARIOS_IMPORTS = [
  CommonModule,
  DataTableComponent,
  HorizontalSelectComponent,
  DynamicFormComponent
] as const;

export const ROLES_IMPORTS = [
  CommonModule,
  DataTableComponent,
  DynamicFormComponent
] as const;

export const GRUPOS_IMPORTS = [
  CommonModule,
  DataTableComponent,
  DynamicFormComponent
] as const;
