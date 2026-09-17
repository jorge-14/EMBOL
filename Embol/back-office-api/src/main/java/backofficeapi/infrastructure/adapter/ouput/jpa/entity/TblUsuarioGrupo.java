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

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioGrupo
 *   Descripción: Entidad JPA para la tabla intermedia TBL_USUARIO_GRUPO (Oracle)
 *   Author Prog: Camila Ledezma / Douglas Javieri
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Camila Ledezma | Creación Inicial como entidad independiente
 *----------------------------------------
 */

@Entity
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USUARIO_GRUPO")
public class TblUsuarioGrupo extends AuditableEntity {

    @Id
    @Column(name = "IIDUSUARIOGRUPO")
    @SequenceGenerator(name = "SEQ_TBL_USUARIO_GRUPO_ID_GENERATOR", sequenceName = "SEQ_TBL_USUARIO_GRUPO_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_USUARIO_GRUPO_ID_GENERATOR")
    private Long iIdUsuarioGrupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDUSUARIO", referencedColumnName = "IIDUSUARIO", nullable = false)
    private TblUsuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IIDGRUPO", referencedColumnName = "IIDGRUPO", nullable = false)
    private TblGrupo grupo;
}
