package backofficeapi.infrastructure.adapter.input.rest.response.tblResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ActionPermissionDto
 *   Descripción: DTO que define una acción de la matriz de accesos
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
public class ActionPermissionDto {
    private String key;
    private String label;
}
