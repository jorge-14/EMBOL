package backofficeapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceModel
 *   Descripción: Modelo de dominio para TblResource (TBL_RECURSO)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblResourceModel {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Long parentResourceId;
    private TblResourceModel parentResource;
    private Boolean deleted;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
}
