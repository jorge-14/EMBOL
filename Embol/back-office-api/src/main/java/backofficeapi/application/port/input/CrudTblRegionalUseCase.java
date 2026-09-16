package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblRegionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblRegionalUseCase
 *   Descripción: Puerto de entrada (caso de uso) para operaciones CRUD de TblRegional (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

public interface CrudTblRegionalUseCase {

    TblRegionalModel createRegional(TblRegionalModel model);

    List<TblRegionalModel> listAllRegionals();

    Optional<TblRegionalModel> getRegionalById(Long id);

    TblRegionalModel updateRegional(Long id, TblRegionalModel model);

    void deleteRegionalById(Long id);

    Page<TblRegionalModel> getListPageRegional(Pageable pageable);
}
