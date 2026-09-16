package backofficeapi.infrastructure.adapter.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalPageResponseDto
 *   Descripción: DTO de respuesta paginada para TblRegionales (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblRegionalPageResponseDto {

    private List<TblRegionalResponseDto> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
