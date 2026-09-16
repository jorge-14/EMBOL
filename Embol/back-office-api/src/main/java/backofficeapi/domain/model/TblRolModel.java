package backofficeapi.domain.model;

import backofficeapi.domain.enums.SEstadoRol;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
@Builder
public class TblRolModel {

    private Long iIdRol;
    private String sNombre;
    private String sDescripcion;
    private Boolean sRolBase;
    private SEstadoRol sEstado;

    public TblRolModel() {

    }

    public TblRolModel(Long iIdRol, String sNombre, String sDescripcion, Boolean sRolBase, SEstadoRol sEstado) {
        this.iIdRol = iIdRol;
        this.sNombre = sNombre;
        this.sDescripcion = sDescripcion;
        this.sRolBase = sRolBase;
        this.sEstado = sEstado;
    }
}
