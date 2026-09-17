# HorizontalMultiSelectComponent (`<app-horizontal-multi-select>`)

Componente de formulario moderno de **selección múltiple** mediante Checkboxes. Muestra su "Label" a la izquierda y el "Input" a la derecha para ahorrar espacio vertical. Incluye buscador interno flotante.

## 📥 Inputs y Models (`[ ]` / `[( )]`)

| Propiedad | Tipo | Defecto | Descripción |
|-----------|------|---------|-------------|
| `label` | `string` | `''` | El texto que aparecerá a la izquierda del selector (Ej: "Estado:"). |
| `options` | `SelectOption[]`| `[]` | Arreglo de opciones en formato `{ label: string, value: any }`. |
| `value` | `model<any[]>` | `[]` | **(Two-Way Binding)**: Un ARREGLO de valores/IDs seleccionados. Se usa con la sintaxis `[(value)]="misFiltros"`. |

## 💡 Comportamiento
- Al no tener nada seleccionado dirá "Seleccionar...".
- Si seleccionas un único elemento, mostrará el nombre de ese elemento (Ej: "Activo").
- Si seleccionas 2 o más elementos, mostrará de forma comprimida "X seleccionados" para no desbordar ni romper la interfaz.
- Incluye buscador integrado: Buscar texto filtra las opciones, pero no deselecciona los elementos que ya tenías checkeados.

## 🚀 Ejemplo de Uso
```html
<app-horizontal-multi-select
  label="Estados Permitidos:"
  [options]="opcionesDeEstado"
  [(value)]="arrayDeFiltrosDeEstado"
></app-horizontal-multi-select>
```
