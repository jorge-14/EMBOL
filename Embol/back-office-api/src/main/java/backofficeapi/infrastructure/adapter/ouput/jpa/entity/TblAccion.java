package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblAccion
 *   Descripción: Entidad JPA para la tabla TBL_ACCION
 *   Author Prog: Douglas Cristhian Javieri Vino
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Douglas Cristhian Javieri Vino | Creación Inicial
 *----------------------------------------
 */

@Entity
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ACCION")
public class TblAccion extends AuditableEntity {

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
