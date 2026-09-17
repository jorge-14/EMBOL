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
@Table(name = "TBL_ROL_RECURSO")
public class TblRolRecurso extends AuditableEntity{
    @Id
    @Column(name = "IIDROLRECURSO")
    @SequenceGenerator(name = "SEQ_TBL_ROL_RECURSO_ID_GENERATOR", sequenceName = "SEQ_TBL_ROL_RECURSO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_ROL_RECURSO_ID_GENERATOR")
    private Long iIdRolRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDRECURSO", referencedColumnName = "IIDRECURSO", nullable = false)
    private TblRecurso iIdRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDROL", referencedColumnName = "IIDROL", nullable = false)
    private TblRol iIdRol;
}
