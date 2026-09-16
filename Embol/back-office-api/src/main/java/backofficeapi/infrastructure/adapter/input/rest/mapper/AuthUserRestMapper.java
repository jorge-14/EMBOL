package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.enums.UserStatus;
import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserRestMapper
 *   Descripción: Mapper REST entre DTOs y AuthUserModel
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO y soporte de paginación
 *----------------------------------------
 */

@Component
public class AuthUserRestMapper {

    public AuthUserModel toModelCreate(AuthUserCreateRequestDto request) {
        if (request == null) {
            return null;
        }
        return AuthUserModel.builder()
                .username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    public AuthUserModel toModelUpdate(AuthUserUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        return AuthUserModel.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .userStatus(request.getUserStatus())
                .build();
    }

    public AuthUserResponseDto toResponse(AuthUserModel model) {
        if (model == null) {
            return null;
        }
        return AuthUserResponseDto.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastname(model.getLastname())
                .email(model.getEmail())
                .userStatus(model.getUserStatus())
                .deleted(model.getDeleted())
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public List<AuthUserResponseDto> toResponseList(List<AuthUserModel> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream()
                .map(this::toResponse)
                .toList();
    }

    public AuthUserPageResponseDto toPageResponse(Page<AuthUserModel> page) {
        if (page == null) {
            return null;
        }
        return AuthUserPageResponseDto.builder()
                .content(toResponseList(page.getContent()))
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
