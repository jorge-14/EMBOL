package backofficeapi.infrastructure.adapter.input.rest.response.tblAccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ActionPermissionDto
 *   Descripción: DTO de columna de acción para la respuesta de accesos
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
public class ActionPermissionDto {
    private String key;
    private String label;
    private String colorClass;
    private String activeColorClass;
}
