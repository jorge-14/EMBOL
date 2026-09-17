package backofficeapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: RoleStatus
 *   Descripción: Enumerador para los estados de Rol
 *   Author Prog: Jorge Luis Choque Callizaya 
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@AllArgsConstructor
public enum RoleStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED");

    private final String stateRole;
}
