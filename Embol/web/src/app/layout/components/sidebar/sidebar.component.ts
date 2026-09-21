import { Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MenuItem } from '../../models/menu.model';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent {
  private router = inject(Router);

  collapsed = signal(false);
  expandedSections = signal<Set<string>>(new Set(['presupuestador', 'configuracion', 'cargas-beneficios']));

  // ── Menú — SOLO /planilla y /users tienen ruta real ──
  menuItems = signal<MenuItem[]>([
    {
      id: 'dashboard',
      label: 'DASHBOARD',
      icon: 'dashboard',
      // route: undefined → sin página implementada
    },
    // {
    //   id: 'simulador',
    //   label: 'SIMULADOR SALARIAL',
    //   icon: 'calculator',
    // },
    {
      id: 'presupuestador',
      label: 'PRESUPUESTADOR',
      icon: 'dollar',
      children: [
        { id: 'compensacion', label: 'COMPENSACIÓN' },
        {
          id: 'cargas-beneficios',
          label: 'CARGAS Y BENEFICIOS',
          children: [
            { id: 'dotacion',        label: 'Dotación', route: '/presupuestador/dotacion' },
            { id: 'beneficios',      label: 'Beneficios al Personal' },
            { id: 'vacaciones',      label: 'Vacaciones' },
            { id: 'provisiones',     label: 'Provisiones y Cargas' },
            { id: 'ropa-trabajo',    label: 'Ropa de Trabajo' },
          ]
        },
      ],
    },
    // {
    //   id: 'forecast',
    //   label: 'FORECAST CMO',
    //   icon: 'trending',
    // },
    // {
    //   id: 'reportes',
    //   label: 'REPORTES',
    //   icon: 'chart',
    // },
    {
      id: 'configuracion',
      label: 'CONFIGURACIÓN',
      icon: 'cog',
      children: [
        { id: 'user-roles',  label: 'Usuarios y Roles', route: '/configuration/user-roles' },
        { id: 'accesos',     label: 'Accesos',     route: '/configuration/accesses' },
      ],
    },
  ]);

  toggleSection(sectionId: string): void {
    const expanded = new Set(this.expandedSections());
    if (expanded.has(sectionId)) {
      expanded.delete(sectionId);
    } else {
      expanded.add(sectionId);
    }
    this.expandedSections.set(expanded);
  }

  isSectionExpanded(sectionId: string): boolean {
    return this.expandedSections().has(sectionId);
  }

  toggleCollapse(): void {
    this.collapsed.update(v => !v);
  }

  isChildActive(item: MenuItem): boolean {
    if (!item.children) return false;
    const currentUrl = this.router.url;
    return item.children.some(child => child.route && currentUrl.startsWith(child.route));
  }

  getIconPath(icon: string): string {
    const icons: Record<string, string> = {
      dashboard:  'M4 5a1 1 0 011-1h4a1 1 0 011 1v4a1 1 0 01-1 1H5a1 1 0 01-1-1V5zm10 0a1 1 0 011-1h4a1 1 0 011 1v4a1 1 0 01-1 1h-4a1 1 0 01-1-1V5zM4 15a1 1 0 011-1h4a1 1 0 011 1v4a1 1 0 01-1 1H5a1 1 0 01-1-1v-4zm10 0a1 1 0 011-1h4a1 1 0 011 1v4a1 1 0 01-1 1h-4a1 1 0 01-1-1v-4z',
      calculator: 'M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z',
      dollar:     'M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z',
      trending:   'M13 7h8m0 0v8m0-8l-8 8-4-4-6 6',
      chart:      'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z',
      cog:        'M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.066 2.573c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.573 1.066c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.066-2.573c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065zM15 12a3 3 0 11-6 0 3 3 0 016 0z',
    };
    return icons[icon] || icons['dashboard'];
  }
}
