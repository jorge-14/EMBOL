package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import jakarta.persistence.Basic;
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
 *   Código de Objeto: AuthRegional
 *   Descripción: Entidad JPA para la tabla AUTH_REGIONAL
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Entity
@Table(name = "AUTH_REGIONAL")
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegional extends AuditableEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AUTH_REGIONAL_ID_GENERATOR", sequenceName = "SEQ_AUTH_REGIONAL_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_REGIONAL_ID_GENERATOR")
    private Long id;

    @Basic
    @Column(name = "CODE", nullable = false, unique = true, length = 50)
    private String code;

    @Basic
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Basic
    @Column(name = "ADDRESS", length = 255)
    private String address;
}
