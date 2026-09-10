// ─── User Feature Imports Enum ───

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table.component';

/** Imports para componentes del feature Users */
export const USER_IMPORTS = [
  CommonModule,
  FormsModule,
  DataTableComponent,
] as const;
