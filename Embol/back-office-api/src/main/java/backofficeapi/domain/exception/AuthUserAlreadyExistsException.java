package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public class AuthUserAlreadyExistsException extends BusinessApiException {
    public AuthUserAlreadyExistsException(String entraId) {
        super(HttpStatus.CONFLICT, "El usuario ya existe con entraId: " + entraId);
    }
}
