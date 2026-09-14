package backofficeapi.infrastructure.adapter.ouput.jpa.mapper;

import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import org.springframework.stereotype.Component;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Component
public class AuthUserMapper {

    public AuthUser toEntity(AuthUserModel model) {
        return AuthUser.builder()
                .id(model.getId())
                .entraId(model.getEntraId())
                .username(model.getUsername())
                .name(model.getName())
                .fatherLastname(model.getFatherLastname())
                .motherLastname(model.getMotherLastname())
                .userStatus(model.getUserStatus())
                .build();
    }

    public AuthUserModel toModel(AuthUser entity) {
        if (entity == null) {
            return null;
        }
        return new AuthUserModel(
                entity.getId(),
                entity.getEntraId(),
                entity.getUsername(),
                entity.getName(),
                entity.getFatherLastname(),
                entity.getMotherLastname(),
                entity.getUserStatus()
        );
    }
}
