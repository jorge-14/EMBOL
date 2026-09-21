package backofficeapi.infrastructure.adapter.input.rest.request.saga;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferencia para crear un grupo mediante SAGA (Local + Entra ID)")
public class CreateGroupSagaRequestDto {

    @Schema(description = "Nombre del Grupo", example = "Grupo IT", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Descripción del Grupo", example = "Equipo de infraestructura tecnológica", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
