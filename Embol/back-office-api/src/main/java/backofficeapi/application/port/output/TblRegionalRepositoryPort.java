package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblRegionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalRepositoryPort
 *   Descripción: Puerto de salida para persistencia de TblRegional (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

public interface TblRegionalRepositoryPort {

    TblRegionalModel saveRegional(TblRegionalModel model);

    List<TblRegionalModel> listAllRegionals();

    Optional<TblRegionalModel> getRegionalById(Long id);

    Optional<TblRegionalModel> getRegionalByCode(String code);

    Page<TblRegionalModel> getListPageRegional(Pageable pageable);
}
