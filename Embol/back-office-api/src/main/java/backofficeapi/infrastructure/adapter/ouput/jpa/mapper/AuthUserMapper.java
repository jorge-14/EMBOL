package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import org.springframework.stereotype.Component;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserMapper
 *   Descripción: Mapper JPA entre entidad AuthUser (TBL_USUARIO) y AuthUserModel
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO y auditoría
 *----------------------------------------
 */

@Component
public class AuthUserMapper {

    public AuthUser toEntity(AuthUserModel model) {
        if (model == null) {
            return null;
        }
        return AuthUser.builder()
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

    public AuthUserModel toModel(AuthUser entity) {
        if (entity == null) {
            return null;
        }
        return AuthUserModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .userStatus(entity.getUserStatus())
                .deleted(entity.isDeleted())
                .version(entity.getVersion())
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedDate(entity.getModifiedDate())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }
}
