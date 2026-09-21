package backofficeapi.infrastructure.adapter.input.rest.request.saga;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferencia para crear un rol mediante SAGA (Local + Entra ID)")
public class CreateRoleSagaRequestDto {

    @Schema(description = "Nombre del Rol", example = "Rol Finanzas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Descripción del Rol", example = "Acceso a reportes financieros", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
