package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.enums.UserStatus;
import backofficeapi.domain.model.TblUsuarioModel;
import backofficeapi.infrastructure.adapter.input.rest.request.TblUsuarioCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.TblUsuarioUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblUsuarioPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblUsuarioResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioRestMapper
 *   Descripción: Mapper REST entre DTOs y TblUsuarioModel con soporte para roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Mapeo de roleIds, groupIds y paginación
 *----------------------------------------
 */

@Component
public class TblUsuarioRestMapper {

    public TblUsuarioModel toModelCreate(TblUsuarioCreateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblUsuarioModel.builder()
                .username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .roleIds(request.getRoleIds())
                .groupIds(request.getGroupIds())
                .build();
    }

    public TblUsuarioModel toModelUpdate(TblUsuarioUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblUsuarioModel.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(request.getUserStatus())
                .roleIds(request.getRoleIds())
                .groupIds(request.getGroupIds())
                .build();
    }

    public TblUsuarioResponseDto toResponse(TblUsuarioModel model) {
        if (model == null) {
            return null;
        }
        return TblUsuarioResponseDto.builder()
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

    public List<TblUsuarioResponseDto> toResponseList(List<TblUsuarioModel> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream()
                .map(this::toResponse)
                .toList();
    }

    public TblUsuarioPageResponseDto toPageResponse(Page<TblUsuarioModel> page) {
        if (page == null) {
            return null;
        }
        return TblUsuarioPageResponseDto.builder()
                .content(toResponseList(page.getContent()))
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
