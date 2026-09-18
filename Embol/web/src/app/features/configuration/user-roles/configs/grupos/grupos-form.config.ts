import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';

export const buildCreateGroupConfig = (): DynamicFormConfig => ({
  title: 'Nuevo Grupo',
  fields: [
    { key: 'nombre', label: 'NOMBRE', type: 'text', placeholder: 'Ej: Operaciones Sur', required: true, colSpan: 2 },
    { key: 'descripcion', label: 'DESCRIPCIÓN', type: 'text', placeholder: 'Describe el propósito del grupo', required: true, colSpan: 2 },
  ],
  submitLabel: 'Guardar',
  cancelLabel: 'Cancelar'
});

export const buildUpdateGroupConfig = (): DynamicFormConfig => ({
  title: 'Editar Grupo',
  fields: [
    { key: 'nombre', label: 'NOMBRE', type: 'text', placeholder: 'Ej: Operaciones Sur', required: true, colSpan: 2 },
    { key: 'descripcion', label: 'DESCRIPCIÓN', type: 'text', placeholder: 'Describe el propósito del grupo', required: true, colSpan: 2 }
  ],
  submitLabel: 'Guardar Cambios',
  cancelLabel: 'Cancelar'
});
