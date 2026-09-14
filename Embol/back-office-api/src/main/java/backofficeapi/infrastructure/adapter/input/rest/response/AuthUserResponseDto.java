package backofficeapi.infrastructure.adapter.input.rest.response;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Getter
@Setter
@Builder
public class AuthUserResponseDto {

    private Long id;
    private String entraId;
    private String username;
    private String name;
    private String fatherLastname;
    private String motherLastname;
    private String userStatus;
}
