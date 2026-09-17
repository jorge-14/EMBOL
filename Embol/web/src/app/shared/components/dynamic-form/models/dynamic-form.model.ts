import { SelectOption } from '../../horizontal-controls/models/select-option.model';

export type FieldType = 'text' | 'number' | 'date' | 'select' | 'multi-select' | 'slider' | 'toggle' | 'textarea' | 'email' | 'password' | 'checkbox' | 'datepicker';

export interface DynamicFormField {
  key: string;
  label: string;
  type: FieldType;
  placeholder?: string;
  required?: boolean;
  options?: SelectOption[]; // Solo para tipo 'select'
  value?: any;
  validators?: any[];
  colSpan?: 1 | 2; // Controlar el ancho en una rejilla de 2 columnas
}

export interface DynamicFormConfig {
  title: string;
  fields: DynamicFormField[];
  submitLabel?: string;
  cancelLabel?: string;
  position?: 'center' | 'right';
}
