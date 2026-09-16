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
@Table(name = "TBL_ACCION")
public class TblAccion {
    @Id
    @Column(name = "IIDACCION")
    @SequenceGenerator(name = "SEQ_TBL_ACCION_ID_GENERATOR", sequenceName = "SEQ_TBL_ACCION_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_ACCION_ID_GENERATOR")
    private Long iIdAccion;

    @Column(name = "SNOMBRE", nullable = false, length = 60)
    private String sNombre;

    @Column(name = "SDESCRIPCION")
    private String sDescripcion;

    @Column(name = "SCODIGO", length = 40)
    private String sCodigo;
}
