package backofficeapi.application.port.output;

import backofficeapi.domain.model.AuthRegionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalRepositoryPort
 *   Descripción: Puerto de salida para persistencia de AuthRegional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public interface AuthRegionalRepositoryPort {

    AuthRegionalModel saveRegional(AuthRegionalModel model);

    List<AuthRegionalModel> listAllRegionals();

    Optional<AuthRegionalModel> getRegionalById(Long id);

    Optional<AuthRegionalModel> getRegionalByCode(String code);

    void deleteRegionalById(Long id);

    Page<AuthRegionalModel> getListPageRegional(Pageable pageable);
}
