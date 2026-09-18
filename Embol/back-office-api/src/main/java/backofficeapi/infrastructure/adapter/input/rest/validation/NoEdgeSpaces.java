package backofficeapi.infrastructure.adapter.input.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 18/09/2026
 */
@Documented
@Constraint(validatedBy = NoEdgeSpacesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoEdgeSpaces {
    String message() default "El valor no puede estar vacío, contener solo espacios en blanco, "
            + "ni tener espacios al inicio o al final";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
