# DataTableComponent (`<app-data-table>`)

Un componente estructural avanzado para renderizar tablas dinámicas. Soporta paginación, edición en línea, filtros personalizados (Content Projection), búsqueda global y renderizado dinámico de tipos de datos (monedas, fechas, badges).

## 📥 Inputs (Variables `[ ]`)

Se envían desde el padre hacia la tabla para configurarla.

| Propiedad | Tipo | Defecto | Descripción |
|-----------|------|---------|-------------|
| `data` | `any[]` | **Requerido** | El arreglo de objetos (filas) que se pintarán en la tabla. |
| `columns` | `DataTableColumn[]`| **Requerido** | Configuración de las columnas (título, qué propiedad de `data` lee, qué formato usa, etc). |
| `showPagination` | `boolean` | `false` | Muestra la barra inferior de paginación. Requiere enviarle `pageInfo`. |
| `pageInfo` | `PageMetadata` | `null` | Objeto devuelto por el Backend con la metadata (total de páginas, página actual, size). |
| `showFilter` | `boolean` | `false` | Muestra la caja de búsqueda global ("Buscar...") arriba a la derecha. |
| `showCustomFilters`| `boolean` | `false` | Activa el portal `<ng-content select="[table-filters]">` para que el padre pueda inyectar sus propios filtros en la parte superior. |
| `headerCssClass` | `string` | `'bg-[#222]'` | Permite sobreescribir el color o diseño del Header (`thead`). |
| `footerCssClass` | `string` | `'bg-[#222]...'`| Permite sobreescribir el color o diseño del Footer/Totales (`tfoot`). |
| `showTotalsRow` | `boolean` | `false` | Muestra una última fila para sumarizar automáticamente las columnas configuradas como `summable`. |
| `rowActions` | `Action[]` | `[]` | Agrega una última columna de "Acciones" con botones (Ej: Editar, Borrar, Ver). |
| `editableMode` | `boolean` | `false` | Activa la funcionalidad de "Doble click para editar celda". |

## 📤 Outputs (Eventos `( )`)

La tabla los emite hacia el padre cuando ocurre una acción del usuario.

| Evento | Emite (Payload) | Descripción |
|--------|-----------------|-------------|
| `pageChange` | `number` | El usuario hizo clic en "Siguiente", "Anterior" o un número de página. |
| `pageSizeChange` | `number` | El usuario cambió el selector de "Filas por pág" (ej. de 5 a 10). |
| `filterChange` | `string` | El usuario tipeó en la caja de Búsqueda Global. (Ya viene con *Debounce* de 500ms integrado para no saturar al backend). |
| `rowActionClick` | `Object` | El usuario hizo click en algún botón de la columna de Acciones. Devuelve qué fila se pulsó y qué acción era. |

## 🚀 Ejemplo de Uso (Main Use)

```html
<app-data-table
  [data]="usersData"
  [columns]="userColumns"
  [showCustomFilters]="true"
  [showFilter]="true"
  [showPagination]="true"
  [pageInfo]="pageData"
  (pageChange)="changePage($event)"
  (filterChange)="onSearch($event)"
>
  <!-- Inyección de filtros personalizados (Content Projection) -->
  <div table-filters class="grid grid-cols-3 gap-6 w-[60%]">
     <app-horizontal-select ...></app-horizontal-select>
     <button>Limpiar</button>
  </div>
</app-data-table>
```
