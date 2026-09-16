package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 16/09/2026
 */

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_GRUPO")
public class TblGrupo {
    @Id
    @Column(name = "IIDGRUPO")
    @SequenceGenerator(name = "SEQ_TBL_GRUPO_ID_GENERATOR", sequenceName = "SEQ_TBL_GRUPO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_GRUPO_ID_GENERATOR")
    private Long iIdGrupo;

    @Column(name = "SNOMBRE", nullable = false, length = 40)
    private String sNombre;

    @Column(name = "SDESCRIPCION")
    private String sDescripcion;

}
