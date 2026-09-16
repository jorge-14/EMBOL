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
@Table(name = "TBL_USUARIO_ROL")
public class TblUsuarioRol extends AuditableEntity{
    @Id
    @Column(name = "IIDIUSUARIOROL")
    @SequenceGenerator(name = "SEQ_TBL_USUARIO_ROL_ID_GENERATOR", sequenceName = "SEQ_TBL_USUARIO_ROL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_USUARIO_ROL_ID_GENERATOR")
    private Long iIdUsuarioRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDROL", referencedColumnName = "IIDROL", nullable = false)
    private TblRol iIdRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDUSUARIO", referencedColumnName = "IIDUSUARIO", nullable = false)
    private TblUsuario iIdUsuario;
}
