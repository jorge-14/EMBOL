// ─── Array Pagination Utility ─────────────────────────────────────────────────
// Pagina cualquier array JavaScript y devuelve una estructura idéntica a la
// que devolvería Spring Boot Page<T>. Cuando el backend esté listo,
// basta con reemplazar la llamada a esta función por un HttpClient.get<Page<T>>().
//
// Uso:
//   import { paginateArray } from '../../../shared/utils/paginate.util';
//   return paginateArray(MY_ARRAY, page, size);
//
// Compatible con: DataTableComponent [pageInfo] input (PageMetadata).

import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Page, PageMetadata } from '../models/pagination.model';

/**
 * Pagina un array JavaScript localmente.
 * @param source  Array completo de datos.
 * @param page    Página solicitada (0-indexed).
 * @param size    Elementos por página.
 * @param latency Latencia simulada en ms para imitar la red. Eliminar en producción.
 */
export function paginateArray<T>(
  source: T[],
  page = 0,
  size = 20,
  latency = 100,
): Observable<Page<T>> {
  const start = page * size;
  const content = source.slice(start, start + size);

  const pageMetadata: PageMetadata = {
    size,
    number: page,
    totalElements: source.length,
    totalPages: Math.ceil(source.length / size),
  };

  const response: Page<T> = {
    content,
    page: pageMetadata,
    number: page,
    size: size,
    totalElements: source.length,
    totalPages: Math.ceil(source.length / size),
    last: (page + 1) >= Math.ceil(source.length / size),
    first: page === 0,
    empty: source.length === 0
  };

  return of(response).pipe(delay(latency));
}
