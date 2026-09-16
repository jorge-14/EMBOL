package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 16/09/2026
 */

@Entity
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_RECURSO")
public class TblRecurso extends AuditableEntity {

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
    @JoinColumn(name = "IIDRECURSOPADRE", referencedColumnName = "IIDRECURSO")
    private TblRecurso iIdRecursoPadre;
}
