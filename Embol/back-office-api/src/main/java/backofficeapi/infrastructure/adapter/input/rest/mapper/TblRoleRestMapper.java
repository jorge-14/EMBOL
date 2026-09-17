package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.PageListRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.TblRoleResponseDto;
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

    public TblRoleModel toModel(TblRoleRequestDto tblRolRequestDto) {
        if (tblRolRequestDto == null) {
            return null;
        }
        TblRoleModel tblRolModel = new TblRoleModel();

        tblRolModel.setSNombre(tblRolRequestDto.getName() != null ? tblRolRequestDto.getName().trim() : null);
        tblRolModel.setSDescripcion(tblRolRequestDto.getDescription() != null ? tblRolRequestDto.getDescription().trim() : null);
        tblRolModel.setSRolBase(tblRolRequestDto.isBaseRole());
        tblRolModel.setSEstado(tblRolRequestDto.getRoleStatus());

        return tblRolModel;
    }

    public TblRoleModel toModelUpdate(TblRoleUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        TblRoleModel tblRolModel = new TblRoleModel();

        tblRolModel.setSNombre(request.getName() != null ? request.getName().trim() : null);
        tblRolModel.setSDescripcion(request.getDescription() != null ? request.getDescription().trim() : null);
        tblRolModel.setSEstado(request.getRoleStatus());

        return tblRolModel;
    }

    public TblRoleResponseDto toResponseDto(TblRoleModel tblRolModel) {
        if (tblRolModel == null) {
            return null;
        }
        return TblRoleResponseDto.builder()
                .id(tblRolModel.getIIdRol())
                .name(tblRolModel.getSNombre())
                .description(tblRolModel.getSDescripcion())
                .baseRole(tblRolModel.getSRolBase())
                .roleStatus(tblRolModel.getSEstado())
                .build();
    }

    public TblRoleResponseDto toAuthRoleResponseDto(TblRoleModel tblRolModel) {
        return toResponseDto(tblRolModel);
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

    public PageListRolResponseDto toPageResponse(TblRoleModel tblRolModel) {
        if (tblRolModel == null) {
            return null;
        }
        return PageListRolResponseDto.builder()
                .id(tblRolModel.getIIdRol())
                .name(tblRolModel.getSNombre())
                .description(tblRolModel.getSDescripcion())
                .baseRole(tblRolModel.getSRolBase())
                .roleStatus(tblRolModel.getSEstado())
                .build();
    }
}
