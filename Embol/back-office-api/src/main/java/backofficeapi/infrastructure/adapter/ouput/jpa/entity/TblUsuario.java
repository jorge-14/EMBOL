package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import backofficeapi.domain.enums.UserStatus;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 *   Código de Objeto: TblUsuario
 *   Descripción: Entidad JPA para la tabla TBL_USUARIO (Oracle).
 *   Entidad que representa a un usuario del sistema, sincronizado
 *                desde Entra ID (Azure AD). Es el punto de partida de la
 *                autorización: obtiene permisos por asignación directa de
 *                roles (TblUsuarioRol) o por pertenencia a grupos (TblUsuarioGrupo).
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Entidad independiente 
 *----------------------------------------
 */

@Entity
@Table(name = "TBL_USUARIO")
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TblUsuario extends AuditableEntity {

    @Id
    @Column(name = "IIDUSUARIO")
    @SequenceGenerator(name = "SEQ_TBL_USUARIO_ID_GENERATOR", sequenceName = "SEQ_TBL_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_USUARIO_ID_GENERATOR")
    private Long id;

    @Basic
    @Column(name = "SNOMBRE", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "SAPELLIDO", nullable = false, length = 100)
    private String lastname;

    @Basic
    @Column(name = "SCORREO", length = 150)
    private String email;

    @Basic
    @Column(name = "SUSUARIO", nullable = false, unique = true, length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "SESTADO", nullable = false, length = 20)
    private UserStatus userStatus;
}
