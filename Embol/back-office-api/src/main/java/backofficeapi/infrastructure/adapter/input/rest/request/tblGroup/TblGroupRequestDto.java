package backofficeapi.infrastructure.adapter.input.rest.request.tblGroup;

import jakarta.validation.constraints.Size;
import lombok.*;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblGroupRequestDto {

    @Size(max = 40, message = "El nombre del grupo no debe exceder los 40 caracteres.")
    private String name;

    @Size(max = 255, message = "La descripción del grupo no debe exceder los 40 caracteres.")
    private String description;

    private String status;
}
