package backofficeapi.infrastructure.adapter.input.rest.request.tblRol;

import backofficeapi.domain.enums.SEstadoRol;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */


@Getter
@Setter
@Builder
public class TblRolRequestDto {

    @Size(max = 40, message = "El nombre no debe exceder los 40 caracteres.")
    private String name;
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres.")
    private String description;
    private boolean baseRole;
    private SEstadoRol roleStatus;
}
