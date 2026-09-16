package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import backofficeapi.domain.enums.SEstadoRol;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ROL")
public class TblRol {

    @Id
    @Column(name = "IIDROL")
    @SequenceGenerator(name = "SEQ_TBL_ROL_ID_GENERATOR", sequenceName = "SEQ_TBL_ROL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_ROL_ID_GENERATOR")
    private Long iIdRol;

    @Column(name = "SNOMBRE", nullable = false, length = 40)
    private String sNombre;

    @Column(name = "SDESCRIPCION")
    private String sDescripcion;

    @Column(name = "SROLBASE", nullable = false, columnDefinition = "NUMBER(1) DEFAULT 0")
    @Convert(converter = NumericBooleanConverter.class)
    @Builder.Default
    private Boolean sRolBase = false;

    @Column(name = "SESTADOROL", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SEstadoRol sEstado;
}
