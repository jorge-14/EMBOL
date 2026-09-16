package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblUsuarioModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioMapper
 *   Descripción: Mapper JPA entre entidad TblUsuario (TBL_USUARIO) y TblUsuarioModel con roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Mapeo de colecciones de roles y grupos
 *----------------------------------------
 */

@Component
public class TblUsuarioMapper {

    public TblUsuario toEntity(TblUsuarioModel model) {
        if (model == null) {
            return null;
        }
        return TblUsuario.builder()
                .id(model.getId())
                .username(model.getUsername())
                .name(model.getName())
                .lastname(model.getLastname())
                .email(model.getEmail())
                .userStatus(model.getUserStatus())
                .deleted(model.getDeleted() != null && model.getDeleted())
                .version(model.getVersion())
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public TblUsuarioModel toModel(TblUsuario entity) {
        if (entity == null) {
            return null;
        }

        List<Long> roleIds = entity.getRoles() != null
                ? entity.getRoles().stream().map(TblRol::getIIdRol).toList()
                : List.of();

        List<String> roleNames = entity.getRoles() != null
                ? entity.getRoles().stream().map(TblRol::getSNombre).toList()
                : List.of();

        List<Long> groupIds = entity.getGrupos() != null
                ? entity.getGrupos().stream().map(TblGrupo::getIIdGrupo).toList()
                : List.of();

        List<String> groupNames = entity.getGrupos() != null
                ? entity.getGrupos().stream().map(TblGrupo::getSNombre).toList()
                : List.of();

        return TblUsuarioModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .userStatus(entity.getUserStatus())
                .roleIds(roleIds)
                .roleNames(roleNames)
                .groupIds(groupIds)
                .groupNames(groupNames)
                .deleted(entity.isDeleted())
                .version(entity.getVersion())
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedDate(entity.getModifiedDate())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }
}
