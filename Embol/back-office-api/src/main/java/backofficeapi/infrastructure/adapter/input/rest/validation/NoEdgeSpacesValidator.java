package backofficeapi.infrastructure.adapter.input.rest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 18/09/2026
 */
public class NoEdgeSpacesValidator implements ConstraintValidator<NoEdgeSpaces, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.trim().isEmpty()) {
            return false;
        }
        return value.equals(value.trim());
    }
}
