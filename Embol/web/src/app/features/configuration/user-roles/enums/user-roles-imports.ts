// ─── UserRoles Feature Imports ────────────────────────────────────────────────

import { CommonModule } from '@angular/common';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table.component';
import { HorizontalSelectComponent } from '../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { DynamicFormComponent } from '../../../../shared/components/dynamic-form/dynamic-form.component';
import { ConfirmModalComponent } from '../../../../shared/components/confirm-modal/confirm-modal.component';

export const USUARIOS_IMPORTS = [
  CommonModule,
  DataTableComponent,
  HorizontalSelectComponent,
  DynamicFormComponent,
  ConfirmModalComponent
] as const;

export const ROLES_IMPORTS = [
  CommonModule,
  DataTableComponent,
  DynamicFormComponent,
  ConfirmModalComponent
] as const;

export const GRUPOS_IMPORTS = [
  CommonModule,
  DataTableComponent,
  DynamicFormComponent,
  ConfirmModalComponent
] as const;
