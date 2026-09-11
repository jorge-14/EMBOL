package backofficeapi.infrastructure.adapter.input.rest.dto;

import backofficeapi.domain.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHttpDTO {
    private int status;
    private String error;
    private ErrorType typeError;
    private String detail;
}
