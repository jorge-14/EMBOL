package backofficeapi.domain.model;

import backofficeapi.domain.enums.RoleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleModel
 *   Descripción: Modelo de dominio para Role (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya 
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblRoleModel {

    private Long iIdRol;
    private String sNombre;
    private String sDescripcion;
    private Boolean sRolBase;
    private RoleStatus sEstado;

    private Boolean deleted;

    public TblRoleModel(Long iIdRol, String sNombre) {
        this.iIdRol = iIdRol;
        this.sNombre = sNombre;
    }

    public void markAsDeleted() {
        this.sEstado = RoleStatus.DELETED;
        this.deleted = true;
    }
}
