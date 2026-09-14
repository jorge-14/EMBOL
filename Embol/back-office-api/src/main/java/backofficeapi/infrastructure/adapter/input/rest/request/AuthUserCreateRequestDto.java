package backofficeapi.infrastructure.adapter.input.rest.request;

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
public class AuthUserCreateRequestDto {

    private String entraId;
    private String username;
    private String name;
    private String fatherLastname;
    private String motherLastname;
}
