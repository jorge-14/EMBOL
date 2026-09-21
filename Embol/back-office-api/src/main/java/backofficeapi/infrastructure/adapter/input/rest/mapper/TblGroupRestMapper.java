package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.input.rest.request.tblGroup.TblGroupRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblGroup.TblGroupResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblGroupRestMapper {

    public TblGroupModel toModel(TblGroupRequestDto tblGroupRequestDto) {
        TblGroupModel model = new TblGroupModel();
        model.setSNombre(tblGroupRequestDto.getName());
        model.setSDescripcion(tblGroupRequestDto.getDescription());
        if ("Inactivo".equalsIgnoreCase(tblGroupRequestDto.getStatus())) {
            model.setDeleted(true);
        } else {
            model.setDeleted(false);
        }
        return  model;
    }

    public TblGroupResponseDto toResponse(TblGroupModel tblGroupModel) {
        return TblGroupResponseDto.builder()
                .id(tblGroupModel.getIIdGrupo())
                .name(tblGroupModel.getSNombre())
                .description(tblGroupModel.getSDescripcion())
                .status(tblGroupModel.getDeleted() != null && tblGroupModel.getDeleted() ? "Inactivo" : "Activo")
                .build();
    }

    public ResponsePage<TblGroupResponseDto> toResponsePage(Page<TblGroupModel> page) {
        return ResponsePage.from(page, this::toResponse);
    }
}
