import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
// import Keycloak from 'keycloak-js';
import { filter } from 'rxjs/operators';
import { BreadcrumbItem, HeaderBadge } from '../../models/menu.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  private router    = inject(Router);
  // private keycloak  = inject(Keycloak);

  breadcrumbs = signal<BreadcrumbItem[]>([]);
  userName    = signal('Usuario');
  userInitials = signal('US');
  showUserMenu = signal(false);

  // Badges configurables para el header
  badges = signal<HeaderBadge[]>([
    { label: 'BP2026 - Oficial',   colorClass: 'bg-gray-700 text-white' },
    { label: 'CBB - Cochabamba',    colorClass: 'bg-gray-700 text-white' },
    { label: 'Oficina Central',     colorClass: 'bg-gray-700 text-white' },
  ]);

  constructor() {
    // Build breadcrumbs from URL
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => this.buildBreadcrumbs());

    this.buildBreadcrumbs();
    this.loadUserInfo();
  }

  private buildBreadcrumbs(): void {
    const url = this.router.url.split('?')[0];
    const segments = url.split('/').filter(s => s);

    const routeLabels: Record<string, string> = {
      'users':        'Usuarios y Roles',
      'planilla':     'Sueldos y Salarios',
      'dashboard':    'Dashboard',
      'simulador':    'Simulador Salarial',
      'reportes':     'Reportes',
      'configuracion':'Configuración',
    };

    const crumbs: BreadcrumbItem[] = [{ label: 'Inicio', route: '/' }];

    // Auto-add parent context based on known route hierarchy
    if (['planilla', 'dotacion', 'comisiones'].includes(segments[0])) {
      crumbs.push({ label: 'Presupuestador' });
    } else if (['users', 'parametros', 'escenarios', 'auditoria'].includes(segments[0])) {
      crumbs.push({ label: 'Configuración' });
    }

    segments.forEach(segment => {
      crumbs.push({
        label: routeLabels[segment] || segment.charAt(0).toUpperCase() + segment.slice(1),
        route: '/' + segment,
      });
    });

    this.breadcrumbs.set(crumbs);
  }

  private async loadUserInfo(): Promise<void> {
    try {
      /*
      const profile = await this.keycloak.loadUserProfile();
      const fullName = `${profile.firstName || ''} ${profile.lastName || ''}`.trim();
      this.userName.set(fullName || profile.username || 'Usuario');
      const initials = ((profile.firstName?.[0] || '') + (profile.lastName?.[0] || '')).toUpperCase();
      this.userInitials.set(initials || 'US');
      */
      this.userName.set('Usuario Invitado');
      this.userInitials.set('UI');
    } catch {
      // Keycloak not available, keep defaults
    }
  }

  toggleUserMenu(): void {
    this.showUserMenu.update(v => !v);
  }

  logout(): void {
    // this.keycloak.logout();
    console.log('Logout placeholder');
  }
}
