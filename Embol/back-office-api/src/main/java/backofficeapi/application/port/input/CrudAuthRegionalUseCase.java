package backofficeapi.application.port.input;

import backofficeapi.domain.model.AuthRegionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudAuthRegionalUseCase
 *   Descripción: Puerto de entrada para casos de uso CRUD de AuthRegional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public interface CrudAuthRegionalUseCase {

    AuthRegionalModel createRegional(AuthRegionalModel model);

    List<AuthRegionalModel> listAllRegionals();

    Optional<AuthRegionalModel> getRegionalById(Long id);

    AuthRegionalModel updateRegional(Long id, AuthRegionalModel model);

    void deleteRegionalById(Long id);

    Page<AuthRegionalModel> getListPageRegional(Pageable pageable);
}
