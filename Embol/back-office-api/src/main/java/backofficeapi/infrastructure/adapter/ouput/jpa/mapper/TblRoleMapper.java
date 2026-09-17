package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleMapper
 *   Descripción: Mapper JPA entre entidad TblRol y TblRoleModel
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRoleMapper {

    public TblRol toEntity(TblRoleModel model) {
        if (model == null) {
            return null;
        }
        TblRol entity = TblRol.builder()
                .iIdRol(model.getIIdRol())
                .sNombre(model.getSNombre())
                .sDescripcion(model.getSDescripcion())
                .sRolBase(model.getSRolBase())
                .sEstado(model.getSEstado())
                .build();
        entity.setDeleted(model.getDeleted() != null && model.getDeleted());
        return entity;
    }

    public TblRoleModel toModel(TblRol entity) {
        if (entity == null) {
            return null;
        }
        return TblRoleModel.builder()
                .iIdRol(entity.getIIdRol())
                .sNombre(entity.getSNombre())
                .sDescripcion(entity.getSDescripcion())
                .sRolBase(entity.getSRolBase())
                .sEstado(entity.getSEstado())
                .deleted(entity.isDeleted())
                .build();
    }
}
