package backofficeapi.infrastructure.adapter.input.rest.request;

import backofficeapi.domain.enums.RoleStatus;
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

    private String name;
    private String description;
    private boolean baseRole;
    private RoleStatus roleStatus;
}
