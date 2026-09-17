import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';

export const buildNuevoGrupoConfig = (): DynamicFormConfig => ({
  title: 'Nuevo Grupo',
  fields: [
    { key: 'nombre', label: 'NOMBRE', type: 'text', placeholder: 'Ej: Operaciones Sur', required: true, colSpan: 2 },
    { key: 'descripcion', label: 'DESCRIPCIÓN', type: 'text', placeholder: 'Describe el propósito del grupo', required: true, colSpan: 2 },
    { key: 'estado', label: 'ESTADO', type: 'select', options: [{label: 'Activo', value: 'Activo'}, {label: 'Inactivo', value: 'Inactivo'}], required: true, colSpan: 2 },
  ],
  submitLabel: 'Guardar',
  cancelLabel: 'Cancelar'
});
