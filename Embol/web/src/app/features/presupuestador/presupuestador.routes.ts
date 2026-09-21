import { Routes } from '@angular/router';

export const presupuestadorRoutes: Routes = [
  {
    path: 'dotacion',
    children: [
      {
        path: '',
        loadComponent: () => import('./dotacion/pages/dotacion/dotacion.component').then(m => m.DotacionComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./dotacion/pages/gestion-detalle/gestion-detalle.component').then(m => m.GestionDetalleComponent)
      },
      {
        path: ':id/vista-completa',
        loadComponent: () => import('./dotacion/pages/vista-completa/vista-completa.component').then(m => m.VistaCompletaComponent)
      }
    ]
  }
];
