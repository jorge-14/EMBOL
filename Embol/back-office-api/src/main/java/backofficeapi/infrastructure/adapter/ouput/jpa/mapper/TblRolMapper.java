package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRolMapper {

    public TblRol toEntity(TblRolModel model) {
        return TblRol.builder()
                .iIdRol(model.getIIdRol())
                .sNombre(model.getSNombre())
                .sDescripcion(model.getSDescripcion())
                .sRolBase(model.getSRolBase())
                .sEstado(model.getSEstado())
                .build();
    }

    public TblRolModel toModel(TblRol entity) {
        return new TblRolModel(
                entity.getIIdRol(),
                entity.getSNombre(),
                entity.getSDescripcion(),
                entity.getSRolBase(),
                entity.getSEstado()
        );
    }
}
