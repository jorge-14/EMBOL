package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 16/09/2026
 *
 * Entidad que representa un grupo organizacional (ej. un área,
 *               sucursal o equipo). Permite asignar usuarios en bloque
 *              (TblUsuarioGrupo) y otorgar permisos sobre recursos a todo
 *               el grupo a la vez (TblGrupoRecurso), sin necesidad de asignar
 *               roles individualmente a cada usuario.
 */

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "TBL_GRUPO")
public class TblGrupo extends AuditableEntity {

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
