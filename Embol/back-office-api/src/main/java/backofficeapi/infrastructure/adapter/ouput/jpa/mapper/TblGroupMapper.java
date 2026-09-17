package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
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
public class TblGroupMapper {

    public TblGrupo toEntity(TblGroupModel model) {
        TblGrupo entity  = TblGrupo.builder()
                .iIdGrupo(model.getIIdGrupo())
                .sNombre(model.getSNombre() != null ? model.getSNombre().trim() : null)
                .sDescripcion(model.getSDescripcion() != null ? model.getSDescripcion().trim() :  null)
                .build();
        entity.setDeleted(model.getDeleted() != null && model.getDeleted());
        return entity;
    }

    public TblGroupModel toModel(TblGrupo entity) {
        return new TblGroupModel(
                entity.getIIdGrupo(),
                entity.getSNombre(),
                entity.getSDescripcion(),
                entity.isDeleted()
        );
    }
}
