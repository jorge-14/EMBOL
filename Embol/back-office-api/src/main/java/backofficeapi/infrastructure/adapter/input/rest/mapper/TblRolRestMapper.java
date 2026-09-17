package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRol.TblRolRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRol.TblRolUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.PageListRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.TblRolResponseDto;
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
        if (tblRolRequestDto == null) {
            return null;
        }
        TblRolModel tblRolModel = new TblRolModel();

        tblRolModel.setSNombre(tblRolRequestDto.getName() != null ? tblRolRequestDto.getName().trim() : null);
        tblRolModel.setSDescripcion(tblRolRequestDto.getDescription() != null ? tblRolRequestDto.getDescription().trim() : null);
        tblRolModel.setSRolBase(tblRolRequestDto.isBaseRole());
        tblRolModel.setSEstado(tblRolRequestDto.getRoleStatus());

        return tblRolModel;
    }

    public TblRolModel toModelUpdate(TblRolUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        TblRolModel tblRolModel = new TblRolModel();

        tblRolModel.setSNombre(request.getName() != null ? request.getName().trim() : null);
        tblRolModel.setSDescripcion(request.getDescription() != null ? request.getDescription().trim() : null);
        tblRolModel.setSEstado(request.getRoleStatus());

        return tblRolModel;
    }

    public TblRolResponseDto toAuthRoleResponseDto(TblRolModel tblRolModel) {
        if (tblRolModel == null) {
            return null;
        }
        return TblRolResponseDto.builder()
                .id(tblRolModel.getIIdRol())
                .name(tblRolModel.getSNombre())
                .description(tblRolModel.getSDescripcion())
                .baseRole(tblRolModel.getSRolBase())
                .roleStatus(tblRolModel.getSEstado())
                .build();
    }

    public ListRoleShortResponse toListRoleShortResponse(TblRolModel model) {
        if (model == null) {
            return null;
        }
        return ListRoleShortResponse.builder()
                .id(model.getIIdRol())
                .name(model.getSNombre())
                .build();
    }

    public PageListRolResponseDto toPageResponse(TblRolModel tblRolModel) {
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
