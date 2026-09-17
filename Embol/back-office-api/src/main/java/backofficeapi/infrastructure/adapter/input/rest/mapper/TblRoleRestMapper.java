package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRestMapper
 *   Descripción: 
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRoleRestMapper {

    public TblRoleModel toModel(TblRoleRequestDto tblRoleRequestDto) {
        if (tblRoleRequestDto == null) {
            return null;
        }
        TblRoleModel tblRoleModel = new TblRoleModel();
        tblRoleModel.setSNombre(tblRoleRequestDto.getName() != null ? tblRoleRequestDto.getName().trim() : null);
        tblRoleModel.setSDescripcion(
                tblRoleRequestDto.getDescription() != null ? tblRoleRequestDto.getDescription().trim() : null);
        tblRoleModel.setSRolBase(tblRoleRequestDto.isBaseRole());
        tblRoleModel.setSEstado(tblRoleRequestDto.getRoleStatus());
        return tblRoleModel;
    }

    public AuthRoleResponseDto toAuthRoleResponseDto(TblRoleModel tblRoleModel) {
        if (tblRoleModel == null) {
            return null;
        }
        return AuthRoleResponseDto.builder()
                .id(tblRoleModel.getIIdRol())
                .name(tblRoleModel.getSNombre())
                .description(tblRoleModel.getSDescripcion())
                .baseRole(tblRoleModel.getSRolBase())
                .roleStatus(tblRoleModel.getSEstado())
                .build();
    }

    public ListRoleShortResponse toListRoleShortResponse(TblRoleModel model) {
        if (model == null) {
            return null;
        }
        return ListRoleShortResponse.builder()
                .id(model.getIIdRol())
                .name(model.getSNombre())
                .build();
    }
}
