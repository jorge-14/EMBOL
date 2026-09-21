// ─── Mock Data — Usuarios, Roles, Grupos ─────────────────────────────────────
// Datos de prueba hasta que el backend esté conectado.

import type { UserRow }  from '../models/users/user.model';
import type { RolRow }   from '../models/roles/rol.model';
import type { GrupoRow } from '../models/grupos/grupo.model';

export type { UserRow, RolRow, GrupoRow };

// ── Usuarios ──────────────────────────────────────────────────────────────────
export const MOCK_USERS: UserRow[] = [
  { id: 1, initials: 'JP', color: 'bg-red-600',    usuario: 'jperez',  nombreCompleto: 'Juan Pérez',   correo: 'jperez@embol.bo',  planta: '', cargo: '', roles: ['Jefe de Planta', 'Administrador'],  grupos: ['IT - Infraestructura', 'Finanzas'], estado: 'Activo'   },
  { id: 2, initials: 'ML', color: 'bg-gray-600',   usuario: 'mlopez',  nombreCompleto: 'María López',  correo: 'mlopez@embol.bo',  planta: '', cargo: '', roles: ['Oficina Central'], grupos: ['Finanzas'],             estado: 'Activo'   },
  { id: 3, initials: 'CV', color: 'bg-purple-600', usuario: 'cvera',   nombreCompleto: 'Carlos Vera',  correo: 'cvera@embol.bo',   planta: '', cargo: '', roles: ['Rol Privado'],     grupos: ['IT - Soporte'],         estado: 'Activo'   },
  { id: 4, initials: 'AR', color: 'bg-green-600',  usuario: 'arios',   nombreCompleto: 'Ana Ríos',     correo: 'arios@embol.bo',   planta: '', cargo: '', roles: ['Finanzas', 'Oficina Central'],        grupos: ['Marketing Digital'],    estado: 'Activo'   },
  { id: 5, initials: 'PS', color: 'bg-blue-900',   usuario: 'psuarez', nombreCompleto: 'Pedro Suárez', correo: 'psuarez@embol.bo', planta: '', cargo: '', roles: ['Consultor'],       grupos: [],                     estado: 'Inactivo' },
];

// ── Roles ─────────────────────────────────────────────────────────────────────
export const MOCK_ROLES: RolRow[] = [
  { id: 1, nombre: 'Administrador',   descripcion: 'Acceso total al sistema',          estado: 'Activo'   },
  { id: 2, nombre: 'Oficina Central', descripcion: 'Acceso consolidado nacional',       estado: 'Activo'   },
  { id: 3, nombre: 'Jefe de Planta',  descripcion: 'Acceso a su planta asignada',       estado: 'Activo'   },
  { id: 4, nombre: 'Finanzas',        descripcion: 'Acceso a reportes financieros',     estado: 'Activo'   },
  { id: 5, nombre: 'Consultor',       descripcion: 'Solo lectura en todos los módulos', estado: 'Inactivo' },
  { id: 6, nombre: 'Rol Privado',     descripcion: 'Acceso a módulos específicos',      estado: 'Activo'   },
];

// ── Grupos ────────────────────────────────────────────────────────────────────
export const MOCK_GROUPS: GrupoRow[] = [
  { id: 1, nombre: 'IT - Infraestructura', descripcion: 'Equipo de infraestructura tecnológica', status: 'Activo' },
  { id: 2, nombre: 'IT - Soporte',         descripcion: 'Soporte técnico al usuario final',       status: 'Activo' },
  { id: 3, nombre: 'Marketing Digital',    descripcion: 'Gestión de redes y campañas',            status: 'Activo' },
  { id: 4, nombre: 'Finanzas',             descripcion: 'Contabilidad y tesorería',               status: 'Activo' },
];
