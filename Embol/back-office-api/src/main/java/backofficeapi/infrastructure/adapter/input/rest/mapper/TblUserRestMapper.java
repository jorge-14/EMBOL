package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.enums.UserStatus;
import backofficeapi.domain.model.TblUserModel;
import backofficeapi.infrastructure.adapter.input.rest.request.tblUser.TblUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblUser.TblUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblUser.TblUserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserRestMapper
 *   Descripción: Mapper REST entre DTOs y TblUserModel con soporte para roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Mapeo de roleIds, groupIds y paginación
 *----------------------------------------
 */

@Component
public class TblUserRestMapper {

    public TblUserModel toModelCreate(TblUserCreateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblUserModel.builder()
                .username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .roleIds(request.getRoleIds())
                .groupIds(request.getGroupIds())
                .build();
    }

    public TblUserModel toModelUpdate(TblUserUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblUserModel.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(request.getUserStatus())
                .roleIds(request.getRoleIds())
                .groupIds(request.getGroupIds())
                .build();
    }

    public TblUserResponseDto toResponse(TblUserModel model) {
        if (model == null) {
            return null;
        }
        return TblUserResponseDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastname(model.getLastname())
                .email(model.getEmail())
                .userStatus(model.getUserStatus())
                .roleIds(model.getRoleIds())
                .groupIds(model.getGroupIds())
                .roleNames(model.getRoleNames())
                .groupNames(model.getGroupNames())
                .deleted(model.getDeleted())
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public List<TblUserResponseDto> toResponseList(List<TblUserModel> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream()
                .map(this::toResponse)
                .toList();
    }
}
