package backofficeapi.infrastructure.adapter.input.rest.request.tblResource;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
 *   Código de Objeto: SaveResourceAccessRequestDto
 *   Descripción: DTO de petición para guardar la matriz de accesos
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
public class SaveResourceAccessRequestDto {

    @NotBlank(message = "El modo ('rol' o 'grupo') es obligatorio")
    private String mode;

    @NotNull(message = "El id del rol o grupo es obligatorio")
    private Long id;

    @Valid
    private List<ResourceSavePermissionDto> permissions;
}
