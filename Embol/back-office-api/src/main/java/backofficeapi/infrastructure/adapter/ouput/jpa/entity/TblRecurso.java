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
@Table(name = "TBL_RECURSO")
public class TblRecurso {
    @Id
    @Column(name = "IIDRECURSO")
    @SequenceGenerator(name = "SEQ_TBL_RECURSO_ID_GENERATOR", sequenceName = "SEQ_TBL_RECURSO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_RECURSO_ID_GENERATOR")
    private Long iIdRecurso;

    @Column(name = "SNOMBRE", nullable = false, length = 100)
    private String sNombre;

    @Column(name = "SDESCRIPCION")
    private String sDescripcion;

    @Column(name = "SICONO", nullable = false, length = 50)
    private String icono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDRECURSOPADRE", referencedColumnName = "IIDRECURSO", nullable = false)
    private TblRecurso iIdRecursoPadre;

}
