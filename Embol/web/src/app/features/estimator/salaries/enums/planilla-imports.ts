// ─── Planilla Feature Imports Enum ───

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DataTableComponent } from '../../../../shared/components/data-table/data-table.component';
import { CurrencyBoPipe } from '../../../../shared/pipes/currency-bo.pipe';
import { DynamicFormComponent } from '../../../../shared/components/dynamic-form/dynamic-form.component';

/** Imports para componentes del feature Planilla */
export const PLANILLA_IMPORTS = [
  CommonModule,
  FormsModule,
  DataTableComponent,
  CurrencyBoPipe,
  DynamicFormComponent,
] as const;
