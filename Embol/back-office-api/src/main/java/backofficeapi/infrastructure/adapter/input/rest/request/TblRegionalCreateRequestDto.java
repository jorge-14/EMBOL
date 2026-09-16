package backofficeapi.infrastructure.adapter.input.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalCreateRequestDto
 *   Descripción: DTO de solicitud para creación de TblRegional (TBL_REGIONAL)
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
public class TblRegionalCreateRequestDto {

    @NotBlank(message = "El código de la regional es obligatorio")
    @Size(max = 50, message = "El código no debe exceder los 50 caracteres")
    private String code;

    @NotBlank(message = "El nombre de la regional es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String description;

    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String address;
}
