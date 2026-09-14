package backofficeapi.infrastructure.adapter.input.rest.dto;

import backofficeapi.domain.enums.ErrorType;
import lombok.AllArgsConstructor;
<<<<<<< HEAD
import lombok.Builder;
=======
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
<<<<<<< HEAD
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ErrorHttpDTO
 *   Descripción: DTO estándar para transferencia de información de error HTTP
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
=======
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
 *----------------------------------------
 */

@Data
<<<<<<< HEAD
@Builder
=======
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHttpDTO {
    private int status;
    private String error;
    private ErrorType typeError;
    private String detail;
}
