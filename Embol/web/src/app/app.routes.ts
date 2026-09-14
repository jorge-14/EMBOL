import { Routes } from '@angular/router';
import { AuthGuard } from './core/auth/auth.guard';
import { MainLayoutComponent } from './layout/components/main-layout/main-layout.component';

export const routes: Routes = [
  // ── Rutas públicas (sin layout) ──
  // { path: 'login', loadChildren: () => import('./public/public.routes').then(m => m.publicRoutes) },

  // ── Rutas autenticadas (con layout: sidebar + header) ──
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
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
];
