# HorizontalDatepickerComponent (`<app-horizontal-datepicker>`)

Componente de selección de fecha moderno y estilizado. Sigue el patrón visual de controles horizontales del proyecto, mostrando el "Label" a la izquierda y el selector a la derecha. Incluye un calendario interactivo completo para la navegación mensual.

## 📥 Inputs y Models (`[ ]` / `[( )]`)

| Propiedad | Tipo | Defecto | Descripción |
|-----------|------|---------|-------------|
| `label` | `string` | `''` | El texto descriptivo a la izquierda (Ej: "Fecha de Nacimiento:"). |
| `placeholder` | `string` | `'Seleccione una fecha...'`| Texto mostrado cuando no hay valor. |
| `value` | `model<string \| null>` | `null` | **(Two-Way Binding)**: Valor en formato string `YYYY-MM-DD`. |

## 💡 Comportamiento
- **Formato**: Maneja internamente las fechas como strings `YYYY-MM-DD` para facilitar la integración con servicios REST, pero las muestra al usuario formateadas como `DD/MM/YYYY` (es-ES).
- **Calendario**: Permite navegar entre meses mediante flechas y seleccionar días específicos. El día actual se resalta visualmente.
- **Limpieza**: Permite limpiar el valor seleccionado, devolviendo `null`.
- **Cierre**: Se cierra automáticamente al seleccionar una fecha o al hacer clic fuera del componente.

## 🚀 Ejemplo de Uso

```html
<app-horizontal-datepicker
  label="Fecha de Ingreso:"
  placeholder="Indique la fecha"
  [(value)]="usuario.fechaIngreso"
></app-horizontal-datepicker>
```

## 🛠️ Integración con DynamicForm
Este componente está registrado para ser usado automáticamente en el `DynamicFormComponent` mediante el tipo de campo `'datepicker'`:

```typescript
{
  key: 'fechaNacimiento',
  label: 'Fecha de Nacimiento',
  type: 'datepicker',
  required: true,
  colSpan: 1
}
```
