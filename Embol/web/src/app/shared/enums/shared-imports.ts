// ─── Shared Imports Enum ───
// Agrupa imports comunes para no repetir en cada standalone component.
//
// Uso:
//   @Component({ imports: [...SHARED_IMPORTS, MiOtroComponente] })

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/** Imports base que todo componente suele necesitar */
export const SHARED_IMPORTS = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
] as const;
