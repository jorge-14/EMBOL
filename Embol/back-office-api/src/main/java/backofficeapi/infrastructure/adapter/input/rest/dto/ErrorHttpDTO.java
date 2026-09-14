package backofficeapi.infrastructure.adapter.input.rest.dto;

import backofficeapi.domain.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResponseBody
 *   Descripción: Envoltorio genérico estándar para respuestas HTTP
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
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
