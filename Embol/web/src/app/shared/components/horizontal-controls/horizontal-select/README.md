# HorizontalSelectComponent (`<app-horizontal-select>`)

Componente de formulario moderno (tipo Combobox) de **selección única**. Muestra su "Label" a la izquierda y el "Input" a la derecha para ahorrar espacio vertical. Incluye buscador interno flotante.

## 📥 Inputs y Models (`[ ]` / `[( )]`)

| Propiedad | Tipo | Defecto | Descripción |
|-----------|------|---------|-------------|
| `label` | `string` | `''` | El texto que aparecerá a la izquierda del selector (Ej: "Rol:"). |
| `placeholder` | `string` | `'Seleccione...'`| El texto a mostrar cuando no hay nada seleccionado en la caja principal. |
| `options` | `SelectOption[]`| `[]` | Arreglo de opciones en formato `{ label: string, value: any }`. |
| `value` | `model<any>` | `null` | **(Two-Way Binding)**: La señal donde se guarda el valor (Generalmente el ID) de la opción seleccionada. Se usa con la sintaxis `[(value)]="miVariable"`. |

## 💡 Comportamiento
- Al hacer clic, se abre un menú flotante con un campo de búsqueda en la parte superior.
- Buscar texto filtra las opciones, pero no borra tu selección actual.
- Al cerrar o seleccionar algo, la caja de búsqueda se limpia sola.

## 🚀 Ejemplo de Uso
```html
<app-horizontal-select
  label="Rol Asignado:"
  placeholder="Selecciona un Rol"
  [options]="misOpcionesDeRol"
  [(value)]="filtroRol"
></app-horizontal-select>
```
