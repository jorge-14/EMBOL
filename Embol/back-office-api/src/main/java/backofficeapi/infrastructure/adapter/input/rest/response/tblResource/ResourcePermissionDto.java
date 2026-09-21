package backofficeapi.infrastructure.adapter.input.rest.response.tblResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResourcePermissionDto
 *   Descripción: DTO de recurso y sus permisos para la respuesta de matriz de accesos
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
public class ResourcePermissionDto {
    private Long id;
    private String name;
    private String icon;
    private Map<String, Boolean> permissions;
}
