package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.TblUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioRol;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserMapper
 *   Descripción: Mapper JPA entre entidad TblUsuario (TBL_USUARIO) y TblUserModel con roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Mapeo con entidades
 *----------------------------------------
 */

@Component
public class TblUserMapper {

    public TblUsuario toEntity(TblUserModel model) {
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
                .createdDate(model.getCreatedDate())
                .createdBy(model.getCreatedBy())
                .modifiedDate(model.getModifiedDate())
                .modifiedBy(model.getModifiedBy())
                .build();
    }

    public TblUserModel toModel(TblUsuario entity) {
        return toModel(entity, List.of(), List.of());
    }

    public TblUserModel toModel(TblUsuario entity, List<TblUsuarioRol> usuarioRoles,
            List<TblUsuarioGrupo> usuarioGrupos) {
        if (entity == null) {
            return null;
        }

        List<Long> roleIds = usuarioRoles != null
                ? usuarioRoles.stream()
                        .filter(ur -> ur.getRol() != null)
                        .map(ur -> ur.getRol().getIIdRol())
                        .toList()
                : List.of();

        List<String> roleNames = usuarioRoles != null
                ? usuarioRoles.stream()
                        .filter(ur -> ur.getRol() != null)
                        .map(ur -> ur.getRol().getSNombre())
                        .toList()
                : List.of();

        List<Long> groupIds = usuarioGrupos != null
                ? usuarioGrupos.stream()
                        .filter(ug -> ug.getGrupo() != null)
                        .map(ug -> ug.getGrupo().getIIdGrupo())
                        .toList()
                : List.of();

        List<String> groupNames = usuarioGrupos != null
                ? usuarioGrupos.stream()
                        .filter(ug -> ug.getGrupo() != null)
                        .map(ug -> ug.getGrupo().getSNombre())
                        .toList()
                : List.of();

        return TblUserModel.builder()
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
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedDate(entity.getModifiedDate())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }
}
