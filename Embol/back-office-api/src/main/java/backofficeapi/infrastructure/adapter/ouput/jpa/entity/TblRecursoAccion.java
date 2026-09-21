package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 16/09/2026
 *
 * Tabla intermedia (many-to-many) que asocia un Recurso con
 *                 una Acción. Define qué acciones específicas están
 *                 disponibles sobre cada recurso (ej. el recurso "Usuarios"
 *                 admite las acciones CREAR, EDITAR, ELIMINAR).
 */
@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_RECURSO_ACCION")
public class TblRecursoAccion extends AuditableEntity{
    @Id
    @Column(name = "IIDRECURSOACCION")
    @SequenceGenerator(name = "SEQ_TBL_RECURSO_ACCION_ID_GENERATOR", sequenceName = "SEQ_TBL_RECURSO_ACCION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_RECURSO_ACCION_ID_GENERATOR")
    private Long iIdRecursoAccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDRECURSO", referencedColumnName = "IIDRECURSO", nullable = false)
    private TblRecurso iIdRecurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDACCION", referencedColumnName = "IIDACCION", nullable = false)
    private TblAccion iIdAccion;
}
