package backofficeapi.infrastructure.adapter.input.rest.response;

import backofficeapi.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserResponseDto
 *   Descripción: DTO de respuesta para TblUser (TBL_USUARIO) con roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Inclusión de roleIds, groupIds, roleNames, groupNames
 *----------------------------------------
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblUserResponseDto {

    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private UserStatus userStatus;
    private List<Long> roleIds;
    private List<Long> groupIds;
    private List<String> roleNames;
    private List<String> groupNames;
    private Boolean deleted;
    private Long version;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
}
