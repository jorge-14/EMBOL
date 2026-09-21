package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblActionModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblActionMapper {

    public TblAccion toEntity(TblActionModel model) {
        TblAccion entity = TblAccion.builder()
                .iIdAccion(model.getIIdAccion())
                .sNombre(model.getSNombre() != null ? model.getSNombre().trim() : null)
                .sDescripcion(model.getSDescripcion() != null ? model.getSDescripcion().trim() :  null)
                .sCodigo(model.getSCodigo())
                .build();

        entity.setDeleted(model.getDeleted() != null && model.getDeleted());
        return entity;
    }

    public TblActionModel toModel(TblAccion entity) {
        return new TblActionModel(
                entity.getIIdAccion(),
                entity.getSNombre(),
                entity.getSDescripcion(),
                entity.getSCodigo(),
                entity.isDeleted()
        );
    }
}
