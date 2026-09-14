package backofficeapi.infrastructure.adapter.ouput.jpa.entity;

import backofficeapi.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_USER")
public class AuthUser {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AUTH_USER_ID_GENERATOR", sequenceName = "SEQ_AUTH_USER_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_USER_ID_GENERATOR")
    private Long id;

    @Basic
    @Column(name = "ENTRA_ID", nullable = false)
    private String entraId;

    @Basic
    @Column(name = "USERNAME", nullable = false, length = 40)
    private String username;

    @Basic
    @Column(name = "NAME", nullable = false, length = 40)
    private String name;

    @Basic
    @Column(name = "FATHER_LASTNAME", length = 60)
    private String fatherLastname;

    @Basic
    @Column(name = "MOTHER_LASTNAME", length = 60)
    private String motherLastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS", nullable = false, length = 20)
    private UserStatus userStatus;
}
