import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  DataTableColumn,
  DataTableTab,
  DataTableRowAction
} from '../../../../../shared/components/data-table/models/data-table.model';
import { DataTableComponent } from '../../../../../shared/components/data-table/data-table.component';

@Component({
  selector: 'app-user-roles-mock',
  standalone: true,
  imports: [CommonModule, DataTableComponent],
  templateUrl: './user-roles-mock.component.html',
})
export class UserRolesMockComponent {
  activeTabId = signal<string>('usuarios');

  tabs = signal<DataTableTab[]>([
    { id: 'usuarios', label: 'Usuarios', active: true, colorClass: 'bg-[#6B6661] text-white shadow-sm hover:bg-[#5A5551]' },
    { id: 'roles',    label: 'Roles',    active: false, colorClass: 'bg-[#6B6661] text-white shadow-sm hover:bg-[#5A5551]' },
    { id: 'grupos',   label: 'Grupos',   active: false, colorClass: 'bg-[#6B6661] text-white shadow-sm hover:bg-[#5A5551]' },
  ]);

  // --- MOCK DATA ---
  users = signal<any[]>([
    { id: 1, usuario: 'jperez@embol.bo', nombre: 'Juan Pérez', roles: ['Jefe de Planta'], grupos: ['IT - Infraestructura'], ultimoAcceso: '15-Jun-2026 14:30', estado: 'Activo', initials: 'JP', color: 'bg-red-600' },
    { id: 2, usuario: 'mlopez@embol.bo', nombre: 'María López', roles: ['Oficina Central'], grupos: ['Finanzas'], ultimoAcceso: '15-Jun-2026 09:15', estado: 'Activo', initials: 'ML', color: 'bg-gray-600' },
    { id: 3, usuario: 'cvera@embol.bo', nombre: 'Carlos Vera', roles: ['Rol Privado'], grupos: ['IT - Soporte'], ultimoAcceso: '10-Jun-2026 11:42', estado: 'Activo', initials: 'CV', color: 'bg-purple-600' },
    { id: 4, usuario: 'arios@embol.bo', nombre: 'Ana Ríos', roles: ['Finanzas'], grupos: ['Marketing Digital'], ultimoAcceso: '14-Jun-2026 16:00', estado: 'Activo', initials: 'AR', color: 'bg-green-600' },
    { id: 5, usuario: 'psuarez@embol.bo', nombre: 'Pedro Suárez', roles: ['Consultor'], grupos: [], ultimoAcceso: '01-Jun-2026 09:30', estado: 'Inactivo', initials: 'PS', color: 'bg-blue-900' },
  ]);

  roles = signal<any[]>([
    { id: 1, nombre: 'Administrador', descripcion: 'Acceso total al sistema', usuarios: 1, estado: 'Activo' },
    { id: 2, nombre: 'Oficina Central', descripcion: 'Acceso consolidado nacional', usuarios: 1, estado: 'Activo' },
    { id: 3, nombre: 'Jefe de Planta', descripcion: 'Acceso a su planta asignada', usuarios: 1, estado: 'Activo' },
    { id: 4, nombre: 'Finanzas', descripcion: 'Acceso a reportes financieros', usuarios: 1, estado: 'Activo' },
    { id: 5, nombre: 'Consultor', descripcion: 'Solo lectura en todos los módulos', usuarios: 1, estado: 'Inactivo' },
    { id: 6, nombre: 'Rol Privado', descripcion: 'Acceso a módulos específicos', usuarios: 1, estado: 'Activo' },
  ]);

  groups = signal<any[]>([
    { id: 1, nombre: 'IT - Infraestructura', descripcion: 'Equipo de infraestructura tecnológica', miembros: 3, estado: 'Activo' },
    { id: 2, nombre: 'IT - Soporte', descripcion: 'Soporte técnico al usuario final', miembros: 5, estado: 'Activo' },
    { id: 3, nombre: 'Marketing Digital', descripcion: 'Equipo de marketing y comunicación', miembros: 4, estado: 'Activo' },
    { id: 4, nombre: 'Finanzas', descripcion: 'Área financiera y contabilidad', miembros: 6, estado: 'Activo' },
  ]);

  // --- COLUMNS ---
  userColumns = signal<DataTableColumn[]>([
    {
      key: 'usuario',
      header: 'Usuario',
      type: 'text',
      cssClass: 'font-medium',
      formatter: (_, row) => `(${row.initials}) ${row.usuario}`
    },
    { key: 'nombre', header: 'Nombre', type: 'text', cssClass: 'font-bold text-gray-900' },
    {
      key: 'roles',
      header: 'Roles',
      type: 'badge',
      badgeColorMap: {
        'Jefe de Planta': 'bg-[#EBF5FF] text-[#1E40AF] border-[#DBEAFE]',
        'Oficina Central': 'bg-[#EBF5FF] text-[#1E40AF] border-[#DBEAFE]',
        'Rol Privado': 'bg-[#EBF5FF] text-[#1E40AF] border-[#DBEAFE]',
        'Finanzas': 'bg-[#EBF5FF] text-[#1E40AF] border-[#DBEAFE]',
        'Consultor': 'bg-[#EBF5FF] text-[#1E40AF] border-[#DBEAFE]',
      },
      formatter: (_, row) => row.roles[0] || '—'
    },
    {
      key: 'grupos',
      header: 'Grupos',
      type: 'badge',
      badgeColorMap: {
        'IT - Infraestructura': 'bg-[#F3F4F6] text-[#374151] border-[#E5E7EB]',
        'Finanzas': 'bg-[#F3F4F6] text-[#374151] border-[#E5E7EB]',
        'IT - Soporte': 'bg-[#F3F4F6] text-[#374151] border-[#E5E7EB]',
        'Marketing Digital': 'bg-[#F3F4F6] text-[#374151] border-[#E5E7EB]',
      },
      formatter: (_, row) => row.grupos[0] || '—'
    },
    { key: 'ultimoAcceso', header: 'Último Acceso', type: 'text', cssClass: 'text-gray-500' },
    {
      key: 'estado',
      header: 'Estado',
      type: 'badge',
      badgeColorMap: {
        'Activo': 'bg-green-50 text-green-600 border border-green-100',
        'Inactivo': 'bg-gray-50 text-gray-500 border border-gray-100',
      }
    }
  ]);

  roleColumns = signal<DataTableColumn[]>([
    {
      key: 'nombre',
      header: 'Nombre',
      type: 'text',
      cssClass: 'font-bold text-gray-900',
      formatter: (_, row) => `🗝️ ${row.nombre}`
    },
    { key: 'descripcion', header: 'Descripción', type: 'text', cssClass: 'text-gray-500' },
    { key: 'usuarios', header: 'Usuarios', type: 'text', align: 'center' },
    {
      key: 'estado',
      header: 'Estado',
      type: 'badge',
      badgeColorMap: {
        'Activo': 'bg-green-50 text-green-600 border border-green-100',
        'Inactivo': 'bg-gray-50 text-gray-500 border border-gray-100',
      }
    }
  ]);

  groupColumns = signal<DataTableColumn[]>([
    {
      key: 'nombre',
      header: 'Nombre',
      type: 'text',
      cssClass: 'font-bold text-gray-900',
      formatter: (_, row) => `📦 ${row.nombre}`
    },
    { key: 'descripcion', header: 'Descripción', type: 'text', cssClass: 'text-gray-500' },
    { key: 'miembros', header: 'Miembros', type: 'text', align: 'center' },
    {
      key: 'estado',
      header: 'Estado',
      type: 'badge',
      badgeColorMap: {
        'Activo': 'bg-green-50 text-green-600 border border-green-100',
        'Inactivo': 'bg-gray-50 text-gray-500 border border-gray-100',
      }
    }
  ]);

  // --- ACTIONS ---
  rowActions = signal<DataTableRowAction[]>([
    {
      icon: '📝',
      tooltip: 'Editar',
      colorClass: 'text-gray-600 hover:bg-gray-100 border border-gray-200 rounded px-2 py-1 text-xs flex items-center gap-1',
      handler: (row) => console.log('Edit', row)
    },
    {
      icon: '👤',
      tooltip: 'Desact.',
      colorClass: 'text-red-500 hover:bg-red-50 border border-red-100 rounded px-2 py-1 text-xs flex items-center gap-1',
      handler: (row) => console.log('Deactivate', row),
      visible: (row) => row.estado === 'Activo'
    },
    {
      icon: '✅',
      tooltip: 'Activar',
      colorClass: 'text-green-500 hover:bg-green-50 border border-green-100 rounded px-2 py-1 text-xs flex items-center gap-1',
      handler: (row) => console.log('Activate', row),
      visible: (row) => row.estado === 'Inactivo'
    }
  ]);

  // --- HELPERS ---
  onTabChange(tab: DataTableTab): void {
    this.tabs.update(ts => ts.map(t => ({ ...t, active: t.id === tab.id })));
    this.activeTabId.set(tab.id);
  }

  get addButtonLabel(): string {
    switch (this.activeTabId()) {
      case 'usuarios': return 'Nuevo Usuario';
      case 'roles': return 'Nuevo Rol';
      case 'grupos': return 'Nuevo Grupo';
      default: return 'Nuevo';
    }
  }
}
