package backofficeapi.domain.model;

import backofficeapi.domain.enums.StateRole;
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
public class AuthRoleModel {

    private Long id;
    private String name;
    private String description;
    private boolean baseRole;
    private StateRole roleStatus;

    public AuthRoleModel() {

    }

    public AuthRoleModel(Long id, String name, String description, boolean baseRole, StateRole roleStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.baseRole = baseRole;
        this.roleStatus = roleStatus;
    }
}
