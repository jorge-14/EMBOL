package backofficeapi.domain.model;

import backofficeapi.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserModel
 *   Descripción: Modelo de dominio para TblUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Integración de roles y grupos
 *----------------------------------------
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblUserModel {

    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private UserStatus userStatus;
    private List<Long> roleIds;
    private List<Long> groupIds;
    private List<String> roleNames;
    private List<String> groupNames;
    private Boolean deleted;
    private Long version;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;

    public void activate() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.userStatus = UserStatus.INACTIVE;
    }

    public void delete() {
        this.userStatus = UserStatus.DELETED;
        this.deleted = true;
    }

    public boolean isActive() {
        return this.userStatus == UserStatus.ACTIVE;
    }
}
