package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import backofficeapi.domain.enums.StateRole;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "AUTH_ROLE")
public class AuthRole {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AUTH_ROLE_ID_GENERATOR", sequenceName = "SEQ_AUTH_ROLE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_ROLE_ID_GENERATOR")
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false, length = 35)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Basic
    @Column(name = "BASE_ROLE", nullable = false)
    private boolean baseRole = false;

    @Basic
    @Column(name = "ROLE_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private StateRole roleStatus;
}
