import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';

export const buildRolConfig = (isEdit: boolean = false): DynamicFormConfig => ({
  title: isEdit ? 'Editar Rol' : 'Nuevo Rol',
  fields: [
    { key: 'nombre', label: 'NOMBRE', type: 'text', placeholder: 'Ej: Auditor Interno', required: true, colSpan: 2 },
    { key: 'descripcion', label: 'DESCRIPCIÓN', type: 'text', placeholder: 'Describe el alcance del rol', required: true, colSpan: 2 },
    { key: 'estado', label: 'ESTADO', type: 'select', options: [{label: 'Activo', value: 'Activo'}, {label: 'Inactivo', value: 'Inactivo'}], required: true, colSpan: 2 },
  ],
  submitLabel: isEdit ? 'Guardar Cambios' : 'Guardar',
  cancelLabel: 'Cancelar'
});
