// ─── Layout Imports Enum ───
// Agrupa los componentes del layout para importar en un solo paso.

import { SidebarComponent } from '../components/sidebar/sidebar.component';
import { HeaderComponent } from '../components/header/header.component';
import { MainLayoutComponent } from '../components/main-layout/main-layout.component';

export const LAYOUT_IMPORTS = [
  SidebarComponent,
  HeaderComponent,
  MainLayoutComponent,
] as const;

// Re-export for convenience
export { SidebarComponent, HeaderComponent, MainLayoutComponent };
