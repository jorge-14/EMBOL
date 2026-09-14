package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.AuthRegionalModel;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRegionalCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRegionalUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRegionalPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRegionalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalRestMapper
 *   Descripción: Mapper REST entre DTOs y AuthRegionalModel
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Component
public class AuthRegionalRestMapper {

    public AuthRegionalModel toModel(AuthRegionalCreateRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return AuthRegionalModel.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .deleted(false)
                .build();
    }

    public AuthRegionalModel toModel(AuthRegionalUpdateRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return AuthRegionalModel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .deleted(dto.getDeleted())
                .build();
    }

    public AuthRegionalResponseDto toResponse(AuthRegionalModel model) {
        if (model == null) {
            return null;
        }
        return AuthRegionalResponseDto.builder()
                .id(model.getId())
                .code(model.getCode())
                .name(model.getName())
                .description(model.getDescription())
                .address(model.getAddress())
                .deleted(model.getDeleted())
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public List<AuthRegionalResponseDto> toResponseList(List<AuthRegionalModel> modelList) {
        if (modelList == null) {
            return List.of();
        }
        return modelList.stream()
                .map(this::toResponse)
                .toList();
    }

    public AuthRegionalPageResponseDto toPageResponse(Page<AuthRegionalModel> page) {
        if (page == null) {
            return null;
        }
        List<AuthRegionalResponseDto> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();

        return AuthRegionalPageResponseDto.builder()
                .content(content)
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
