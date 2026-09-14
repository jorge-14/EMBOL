package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public class AuthUserNotFoundException extends BusinessApiException {
    public AuthUserNotFoundException(String entraId) {
        super(HttpStatus.NOT_FOUND, "Usuario no encontrado con EntraId: " + entraId);
    }
}
