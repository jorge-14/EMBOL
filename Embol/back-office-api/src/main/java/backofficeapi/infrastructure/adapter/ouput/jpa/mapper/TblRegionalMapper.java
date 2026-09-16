package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblRegionalModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRegional;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalMapper
 *   Descripción: Mapper JPA entre entidad TblRegional y TblRegionalModel
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRegionalMapper {

    public TblRegional toEntity(TblRegionalModel model) {
        if (model == null) {
            return null;
        }
        return TblRegional.builder()
                .id(model.getId())
                .code(model.getCode())
                .name(model.getName())
                .description(model.getDescription())
                .address(model.getAddress())
                .deleted(model.getDeleted() != null && model.getDeleted())
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public TblRegionalModel toModel(TblRegional entity) {
        if (entity == null) {
            return null;
        }
        return TblRegionalModel.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .address(entity.getAddress())
                .deleted(entity.isDeleted())
                .version(entity.getVersion())
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedDate(entity.getModifiedDate())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }
}
