package backofficeapi.application.port.input.group;

import backofficeapi.domain.model.TblGroupModel;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface FindGroupByIdUseCase {

    TblGroupModel getInformationGroupById(Long id);

}
