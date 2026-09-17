package backofficeapi.infrastructure.adapter.input.rest.request.tblRole;

import backofficeapi.domain.enums.RoleStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRequestDto
 *   Descripción: DTO de solicitud para crear/actualizar Role (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblRoleRequestDto {

    @Size(max = 40, message = "El nombre no debe exceder los 40 caracteres.")
    private String name;
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres.")
    private String description;
    private boolean baseRole;
    private RoleStatus roleStatus;
}
