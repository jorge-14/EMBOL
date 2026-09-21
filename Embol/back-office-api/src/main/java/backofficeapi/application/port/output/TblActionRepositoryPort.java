package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblActionModel;
import java.util.Optional;

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
public interface TblActionRepositoryPort {

    Optional<TblActionModel> findByCode(String code);
    TblActionModel saveAction(TblActionModel tblActionModel);
}
