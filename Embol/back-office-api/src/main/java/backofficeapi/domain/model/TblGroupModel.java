package backofficeapi.domain.model;

import lombok.*;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TblGroupModel {

    private Long iIdGrupo;
    private String sNombre;
    private String sDescripcion;

    private Boolean deleted;

    public TblGroupModel() {
    }

    public TblGroupModel(Long id, String name, String description) {
        this.iIdGrupo = id;
        this.sNombre = name;
        this.sDescripcion = description;
    }
}
