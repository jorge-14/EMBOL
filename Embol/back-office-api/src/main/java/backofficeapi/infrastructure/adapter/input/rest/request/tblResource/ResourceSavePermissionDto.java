package backofficeapi.infrastructure.adapter.input.rest.request.tblResource;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResourceSavePermissionDto
 *   Descripción: DTO para guardar permisos por recurso
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceSavePermissionDto {

    @NotNull(message = "El resourceId es obligatorio")
    private Long resourceId;

    private List<String> grantedActionCodes;
}
