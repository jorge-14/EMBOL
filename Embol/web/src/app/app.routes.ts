import { Routes } from '@angular/router';
import { MsalGuard } from '@azure/msal-angular';
import { MainLayoutComponent } from './layout/components/main-layout/main-layout.component';


export const routes: Routes = [
  // ── Rutas públicas (sin layout) ──


  // ── Rutas autenticadas (con layout: sidebar + header) ──
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [MsalGuard],
    children: [
      { path: '', redirectTo: 'configuration/user-roles', pathMatch: 'full' },
      {
        path: 'configuration/user-roles',
        loadChildren: () => import('./features/configuration/user-roles/user-roles.routes').then(m => m.userRolesRoutes),
      },
      {
        path: 'configuration/accesses',
        loadChildren: () => import('./features/configuration/accesses/accesses.routes').then(m => m.accessRoutes),
      },
    ],
  },

  // Ruta comodín para capturar errores 404 o rutas no encontradas
  { path: '**', redirectTo: '' }
];
