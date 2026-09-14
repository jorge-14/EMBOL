package backofficeapi.infrastructure.adapter.input.rest.mapper;

import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserResponseDto;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Component
public class AuthUserRestMapper {

    public AuthUserModel toModelCreate(AuthUserCreateRequestDto request) {
        return new AuthUserModel(
                request.getEntraId(),
                request.getUsername(),
                request.getName(),
                request.getFatherLastname(),
                request.getMotherLastname()
        );
    }

    public AuthUserModel toModelUpdate(AuthUserUpdateRequestDto request) {
        AuthUserModel authUserModel = new AuthUserModel();
        authUserModel.setEntraId(request.getEntraId());
        authUserModel.setUsername(request.getUsername());
        authUserModel.setName(request.getName());
        authUserModel.setFatherLastname(request.getFatherLastname());
        authUserModel.setMotherLastname(request.getMotherLastname());
        return authUserModel;
    }

    public AuthUserResponseDto toAuthUserResponseDto(AuthUserModel authUserModel) {
        return AuthUserResponseDto.builder()
                .id(authUserModel.getId())
                .entraId(authUserModel.getEntraId())
                .username(authUserModel.getUsername())
                .name(authUserModel.getName())
                .fatherLastname(authUserModel.getFatherLastname())
                .motherLastname(authUserModel.getMotherLastname())
                .userStatus(authUserModel.getUserStatus().toString())
                .build();
    }

    public List<AuthUserResponseDto> toAuthUserResponseDtoList(List<AuthUserModel> authUserModelList) {
        if (authUserModelList == null) {
            return List.of();
        }
        return authUserModelList.stream()
                .map(this::toAuthUserResponseDto)
                .toList();
    }
}
