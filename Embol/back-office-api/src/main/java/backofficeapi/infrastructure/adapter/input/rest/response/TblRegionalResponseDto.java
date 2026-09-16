package backofficeapi.infrastructure.adapter.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalResponseDto
 *   Descripción: DTO de respuesta para TblRegional (TBL_REGIONAL)
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
public class TblRegionalResponseDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String address;
    private Boolean deleted;
    private Long version;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
}
