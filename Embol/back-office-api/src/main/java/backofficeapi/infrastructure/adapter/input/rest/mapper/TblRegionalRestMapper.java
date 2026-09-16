package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblRegionalModel;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRegionalCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRegionalUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblRegionalPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblRegionalResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalRestMapper
 *   Descripción: Mapper REST entre DTOs y TblRegionalModel
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRegionalRestMapper {

    public TblRegionalModel toModel(TblRegionalCreateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblRegionalModel.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .build();
    }

    public TblRegionalModel toModel(TblRegionalUpdateRequestDto request) {
        if (request == null) {
            return null;
        }
        return TblRegionalModel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .deleted(request.getDeleted())
                .build();
    }

    public TblRegionalResponseDto toResponse(TblRegionalModel model) {
        if (model == null) {
            return null;
        }
        return TblRegionalResponseDto.builder()
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

    public TblRegionalPageResponseDto toPageResponse(Page<TblRegionalModel> page) {
        if (page == null) {
            return null;
        }
        List<TblRegionalResponseDto> content = page.getContent().stream()
                .map(this::toResponse)
                .toList();

        return TblRegionalPageResponseDto.builder()
                .content(content)
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
