package backofficeapi.infrastructure.adapter.input.rest.request.tblRol;

import backofficeapi.domain.enums.SEstadoRol;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRolUpdateRequestDto
 *   Descripción: DTO de solicitud para actualización de TblRol (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblRolUpdateRequestDto {

    @Size(max = 40, message = "El nombre no debe exceder los 40 caracteres.")
    private String name;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres.")
    private String description;
    private SEstadoRol roleStatus;
}
