package backofficeapi.infrastructure.adapter.input.rest.response;

import backofficeapi.domain.enums.RoleStatus;
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
public class AuthRoleResponseDto {

    private Long id;
    private String name;
    private String description;
    private boolean baseRole;
    private RoleStatus roleStatus;
}
