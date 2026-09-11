package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.AuthRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
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
public class AuthRoleRestMapper {

    public AuthRoleModel toModel(AuthRoleRequestDto authRoleRequestDto) {
        AuthRoleModel authRoleModel = new AuthRoleModel();

        authRoleModel.setName(authRoleRequestDto.getName());
        authRoleModel.setDescription(authRoleRequestDto.getDescription());
        authRoleModel.setBaseRole(authRoleRequestDto.isBaseRole());
        authRoleModel.setRoleStatus(authRoleRequestDto.getRoleStatus());

        return authRoleModel;
    }

    public AuthRoleResponseDto toAuthRoleResponseDto(AuthRoleModel authRoleModel) {
        return AuthRoleResponseDto.builder()
                .id(authRoleModel.getId())
                .name(authRoleModel.getName())
                .description(authRoleModel.getDescription())
                .baseRole(authRoleModel.isBaseRole())
                .roleStatus(authRoleModel.getRoleStatus())
                .build();
    }
}
