package backofficeapi.infrastructure.adapter.input.rest.request.saga;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Objeto de transferencia para crear un usuario mediante SAGA (Local + Entra ID)")
public class CreateUserSagaRequestDto {

    @Schema(description = "Nombre de usuario o alias de inicio de sesión", example = "jperez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Nombres del usuario", example = "Juan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Apellidos del usuario", example = "Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastname;

    @Schema(description = "Correo electrónico institucional (UPN en Entra ID)", example = "jperez@embol.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Lista de identificadores de roles asignados", example = "[1, 2]")
    private List<Long> roleIds;

    @Schema(description = "Lista de identificadores de grupos asignados", example = "[10]")
    private List<Long> groupIds;
}
