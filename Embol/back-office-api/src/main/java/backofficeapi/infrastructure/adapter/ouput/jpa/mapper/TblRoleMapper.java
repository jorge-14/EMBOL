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
        return TblRol.builder()
                .iIdRol(model.getIIdRol())
                .sNombre(model.getSNombre())
                .sDescripcion(model.getSDescripcion())
                .sRolBase(model.getSRolBase())
                .sEstado(model.getSEstado())
                .build();
    }

    public TblRoleModel toModel(TblRol entity) {
        if (entity == null) {
            return null;
        }
        return new TblRoleModel(
                entity.getIIdRol(),
                entity.getSNombre(),
                entity.getSDescripcion(),
                entity.getSRolBase(),
                entity.getSEstado());
    }
}
