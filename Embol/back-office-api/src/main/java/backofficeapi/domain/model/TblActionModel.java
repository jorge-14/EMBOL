package backofficeapi.domain.model;

import lombok.AllArgsConstructor;
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
 *   21.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TblActionModel {

    private Long iIdAccion;
    private String sNombre;
    private String sDescripcion;
    private String sCodigo;

    private Boolean deleted;

    public TblActionModel() {
    }

    public TblActionModel(Long id, String name, String description, String code) {
        this.iIdAccion = id;
        this.sNombre = name;
        this.sDescripcion = description;
        this.sCodigo = code;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }
}
