package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import backofficeapi.domain.enums.RoleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.type.NumericBooleanConverter;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción: Entidad que representa un rol de negocio (ej. ADMINISTRADOR,
 *                SUPERVISOR). Agrupa un conjunto de permisos sobre recursos
 *                (TblRolRecurso) y se asigna a uno o varios usuarios
 *                (TblUsuarioRol). El flag sRolBase distingue los roles
 *                predefinidos del sistema de los creados manualmente.
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
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_ROL")
public class TblRol extends AuditableEntity {

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
    private RoleStatus sEstado;
}
