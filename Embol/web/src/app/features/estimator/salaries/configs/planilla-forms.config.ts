import { DynamicFormConfig } from "../../../../shared/components/dynamic-form/models/dynamic-form.model";

export function getAddPersonalFormConfig(): DynamicFormConfig {
  return {
    title: 'Añadir Nuevo Personal',
    submitLabel: 'Crear Registro',
    fields: [
      { key: 'nroPers', label: 'Nro. Personal', type: 'number', required: true, colSpan: 1 },
      { key: 'nombre', label: 'Nombre Completo', type: 'text', required: true, colSpan: 1 },
      { key: 'familia', label: 'Familia', type: 'text', required: true, colSpan: 1 },
      { key: 'cargo', label: 'Cargo', type: 'text', required: true, colSpan: 1 },
      {
        key: 'area',
        label: 'Área',
        type: 'select',
        required: true,
        colSpan: 1,
        options: [
          { label: 'Administración', value: 'ADM' },
          { label: 'Producción', value: 'PROD' },
          { label: 'Comercial', value: 'COM' },
          { label: 'Finanzas', value: 'FIN' },
          { label: 'Mantenimiento', value: 'MANT' },
        ]
      },
      { key: 'haberBasico', label: 'Haber Básico', type: 'number', required: true, colSpan: 1 },
      { key: 'fechaIngreso', label: 'Fecha de Ingreso', type: 'datepicker', required: true, colSpan: 1 },
    ]
  };
}

export function getSettingsFormConfig(): DynamicFormConfig {
  return {
    title: 'Configuración de Planilla',
    submitLabel: 'Aplicar Cambios',
    fields: [
      {
        key: 'mes',
        label: 'Mes de Proceso',
        type: 'select',
        required: true,
        colSpan: 1,
        options: [
          { label: 'Enero', value: '1' },
          { label: 'Febrero', value: '2' },
          { label: 'Marzo', value: '3' },
          { label: 'Abril', value: '4' },
          { label: 'Mayo', value: '5' },
          { label: 'Junio', value: '6' },
          { label: 'Julio', value: '7' },
          { label: 'Agosto', value: '8' },
          { label: 'Septiembre', value: '9' },
          { label: 'Octubre', value: '10' },
          { label: 'Noviembre', value: '11' },
          { label: 'Diciembre', value: '12' },
        ]
      },
      { key: 'tipoCambio', label: 'Tipo de Cambio', type: 'number', required: true, colSpan: 1, value: 6.96 },
      { key: 'observaciones', label: 'Observaciones', type: 'textarea', colSpan: 2 },
    ]
  };
}
