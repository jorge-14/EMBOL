// ─── UserRoles Feature Imports ────────────────────────────────────────────────

import { CommonModule } from '@angular/common';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table.component';

/** Imports compartidos para componentes del feature UserRoles */
export const USER_ROLES_IMPORTS = [
  CommonModule,
  DataTableComponent,
] as const;
