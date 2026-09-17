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
@Table(name = "TBL_GRUPO_RECURSO")
public class TblGrupoRecurso extends AuditableEntity{
    @Id
    @Column(name = "IIDGRUPORECURSO")
    @SequenceGenerator(name = "SEQ_TBL_GRUPO_RECURSO_ID_GENERATOR", sequenceName = "SEQ_TBL_GRUPO_RECURSO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_GRUPO_RECURSO_ID_GENERATOR")
    private Long iIdGrupoRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDRECURSO", referencedColumnName = "IIDRECURSO", nullable = false)
    private TblRecurso iIdRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDGRUPO", referencedColumnName = "IIDGRUPO", nullable = false)
    private TblGrupo iIdeGrupo;
}
