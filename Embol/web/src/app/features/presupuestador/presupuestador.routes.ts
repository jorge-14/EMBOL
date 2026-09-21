import { Routes } from '@angular/router';

export const presupuestadorRoutes: Routes = [
  {
    path: 'dotacion',
    loadComponent: () => import('./dotacion/pages/dotacion/dotacion.component').then(m => m.DotacionComponent)
  }
];
