import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';
import { RolRow } from '../../models/roles/rol.model';
import { GrupoRow } from '../../models/grupos/grupo.model';

export const buildNuevoUsuarioConfig = (roles: RolRow[], groups: GrupoRow[]): DynamicFormConfig => ({
  title: 'Nuevo Usuario',
  fields: [
    { key: 'nombre', label: 'NOMBRE COMPLETO', type: 'text', placeholder: 'Ej: María Condori', required: true, colSpan: 2 },
    { key: 'email', label: 'EMAIL', type: 'email', placeholder: 'm.condori@embol.bo', required: true, colSpan: 2 },
    { key: 'estado', label: 'ESTADO', type: 'select', options: [{label: 'Activo', value: 'Activo'}, {label: 'Inactivo', value: 'Inactivo'}], required: true, colSpan: 2 },
    { key: 'roles', label: 'ROLES ASIGNADOS', type: 'multi-select', options: roles.map(r => ({label: r.nombre, value: r.id})), colSpan: 2 },
    { key: 'grupos', label: 'GRUPOS ASIGNADOS', type: 'multi-select', options: groups.map(g => ({label: g.nombre, value: g.id})), colSpan: 2 },
  ],
  submitLabel: 'Guardar',
  cancelLabel: 'Cancelar'
});
