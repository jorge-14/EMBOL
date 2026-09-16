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
@Table(name = "TBL_USUARIO_GRUPO")
public class TblUsuarioGrupo extends AuditableEntity{

    @Id
    @Column(name = "IIDIUSUARIOGRUPO")
    @SequenceGenerator(name = "SEQ_TBL_USUARIO_GRUPO_ID_GENERATOR", sequenceName = "SEQ_TBL_USUARIO_GRUPO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_USUARIO_GRUPO_ID_GENERATOR")
    private Long iIdUsuarioGrupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDGRUPO", referencedColumnName = "IIDGRUPO", nullable = false)
    private TblGrupo iIdGrupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDROL", referencedColumnName = "IIDROL", nullable = false)
    private TblRol iIdRol;
}
