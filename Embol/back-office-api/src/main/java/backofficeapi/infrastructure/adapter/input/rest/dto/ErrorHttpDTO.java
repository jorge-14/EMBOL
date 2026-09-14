package backofficeapi.infrastructure.adapter.input.rest.dto;

import backofficeapi.domain.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ErrorHttpDTO
 *   Descripción: DTO estándar para transferencia de información de error HTTP
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHttpDTO {
    private int status;
    private String error;
    private ErrorType typeError;
    private String detail;
}
