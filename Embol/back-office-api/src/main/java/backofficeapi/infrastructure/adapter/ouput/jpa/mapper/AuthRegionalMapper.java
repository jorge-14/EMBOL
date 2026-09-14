package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.AuthRegionalModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthRegional;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalMapper
 *   Descripción: Mapper entre AuthRegional (JPA) y AuthRegionalModel (Dominio)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Component
public class AuthRegionalMapper {

    public AuthRegionalModel toModel(AuthRegional entity) {
        if (entity == null) {
            return null;
        }
        return AuthRegionalModel.builder()
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

    public AuthRegional toEntity(AuthRegionalModel model) {
        if (model == null) {
            return null;
        }
        return AuthRegional.builder()
                .id(model.getId())
                .code(model.getCode())
                .name(model.getName())
                .description(model.getDescription())
                .address(model.getAddress())
                .deleted(model.getDeleted() != null ? model.getDeleted() : false)
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }
}
