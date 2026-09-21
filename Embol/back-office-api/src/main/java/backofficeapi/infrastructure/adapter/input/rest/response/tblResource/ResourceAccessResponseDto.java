package backofficeapi.infrastructure.adapter.input.rest.response.tblResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResourceAccessResponseDto
 *   Descripción: DTO de respuesta principal para la matriz de accesos y recursos
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
public class ResourceAccessResponseDto {
    private String mode;
    private Long id;
    private String name;
    private int totalGrantedPermissions;
    private List<ActionPermissionDto> actions;
    private List<GroupPermissionDto> groups;
}
