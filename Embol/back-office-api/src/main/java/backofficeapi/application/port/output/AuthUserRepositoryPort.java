package backofficeapi.application.port.output;

import backofficeapi.domain.model.AuthUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserRepositoryPort
 *   Descripción: Puerto de salida para persistencia de AuthUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO y paginación
 *----------------------------------------
 */

public interface AuthUserRepositoryPort {

    AuthUserModel save(AuthUserModel authUserModel);

    Optional<AuthUserModel> findById(Long id);

    Optional<AuthUserModel> findByUsername(String username);

    Optional<AuthUserModel> findByEmail(String email);

    List<AuthUserModel> findAll();

    Page<AuthUserModel> findPage(Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
