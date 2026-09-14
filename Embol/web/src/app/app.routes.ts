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
      { path: '', redirectTo: 'estimator/salaries', pathMatch: 'full' },
      {
        path: 'configuration/users',
        loadChildren: () => import('./features/configuration/users/users.routes').then(m => m.userRoutes),
      },
      {
        path: 'estimator/salaries',
        loadChildren: () => import('./features/estimator/salaries/planilla.routes').then(m => m.planillaRoutes),
      },
    ],
  },
  
  // Ruta comodín para capturar errores 404 o rutas no encontradas
  { path: '**', redirectTo: '' }
];
