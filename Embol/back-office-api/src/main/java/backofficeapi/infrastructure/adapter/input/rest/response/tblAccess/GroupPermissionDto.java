package backofficeapi.infrastructure.adapter.input.rest.response.tblAccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GroupPermissionDto
 *   Descripción: DTO de módulo/grupo de recursos para la respuesta de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupPermissionDto {
    private Long id;
    private String name;
    private List<ResourcePermissionDto> resources;
}
