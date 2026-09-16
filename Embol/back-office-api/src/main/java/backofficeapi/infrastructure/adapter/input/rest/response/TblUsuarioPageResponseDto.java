package backofficeapi.infrastructure.adapter.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioPageResponseDto
 *   Descripción: DTO de respuesta paginada para TblUsuarios (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioPageResponseDto y paginación
 *----------------------------------------
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblUsuarioPageResponseDto {

    private List<TblUsuarioResponseDto> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
