package backofficeapi.infrastructure.adapter.input.rest.response.tblRole;

import backofficeapi.domain.enums.SEstadoRol;
import lombok.*;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 17/09/2026
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageListRolResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean baseRole;
    private SEstadoRol roleStatus;
}
