package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.input.rest.request.tblGroup.TblGroupRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblGroup.TblGroupResponseDto;
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
        return  model;
    }

    public TblGroupResponseDto toResponse(TblGroupModel tblGroupModel) {
        return TblGroupResponseDto.builder()
                .id(tblGroupModel.getIIdGrupo())
                .name(tblGroupModel.getSNombre())
                .description(tblGroupModel.getSDescripcion())
                .build();
    }
}
