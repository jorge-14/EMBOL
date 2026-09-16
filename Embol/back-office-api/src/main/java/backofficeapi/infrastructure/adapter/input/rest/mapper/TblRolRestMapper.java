package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRolRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
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
public class TblRolRestMapper {

    public TblRolModel toModel(TblRolRequestDto tblRolRequestDto) {
        TblRolModel tblRolModel = new TblRolModel();

        tblRolModel.setSNombre(tblRolRequestDto.getName() != null ? tblRolRequestDto.getName().trim() : null);
        tblRolModel.setSDescripcion(tblRolRequestDto.getDescription() != null ? tblRolRequestDto.getDescription().trim() : null);
        tblRolModel.setSRolBase(tblRolRequestDto.isBaseRole());
        tblRolModel.setSEstado(tblRolRequestDto.getRoleStatus());

        return tblRolModel;
    }

    public AuthRoleResponseDto toAuthRoleResponseDto(TblRolModel tblRolModel) {
        return AuthRoleResponseDto.builder()
                .id(tblRolModel.getIIdRol())
                .name(tblRolModel.getSNombre())
                .description(tblRolModel.getSDescripcion())
                .baseRole(tblRolModel.getSRolBase())
                .roleStatus(tblRolModel.getSEstado())
                .build();
    }

    public ListRoleShortResponse toListRoleShortResponse(TblRolModel model) {
        return ListRoleShortResponse.builder()
                .id(model.getIIdRol())
                .name(model.getSNombre())
                .build();
    }
}
