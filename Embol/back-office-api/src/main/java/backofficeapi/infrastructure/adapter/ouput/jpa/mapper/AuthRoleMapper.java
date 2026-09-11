package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.AuthRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthRole;
import org.springframework.stereotype.Component;

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

@Component
public class AuthRoleMapper {

    public AuthRole toEntity(AuthRoleModel model) {
        return AuthRole.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .baseRole(model.isBaseRole())
                .roleStatus(model.getRoleStatus())
                .build();
    }

    public AuthRoleModel toModel(AuthRole entity) {
        return new AuthRoleModel(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.isBaseRole(),
                entity.getRoleStatus()
        );
    }
}
