package backofficeapi.infrastructure.adapter.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalPageResponseDto
 *   Descripción: DTO de respuesta paginada para AuthRegionales
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
public class AuthRegionalPageResponseDto {

    private List<AuthRegionalResponseDto> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
