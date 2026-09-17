// ─── Standard Pagination Models ───
// Compatible con Spring Boot Page<T>

export interface PageMetadata {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
}

export interface Page<T> {
  content: T[];
  page: PageMetadata | null; // Mantener por compatibilidad si se usa en otros lados

  // Spring Boot / ResponsePage fields
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
  empty: boolean;
}
