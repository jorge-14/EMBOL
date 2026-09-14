package backofficeapi.infrastructure.adapter.input.rest.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalUpdateRequestDto
 *   Descripción: DTO de solicitud para actualización de AuthRegional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegionalUpdateRequestDto {

    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String description;

    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String address;

    private Boolean deleted;
}
