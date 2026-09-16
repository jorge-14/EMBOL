package backofficeapi.application.port.input;

import backofficeapi.domain.model.AuthUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudAuthUserUseCase
 *   Descripción: Puerto de entrada (caso de uso) para operaciones CRUD de AuthUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO y paginación
 *----------------------------------------
 */

public interface CrudAuthUserUseCase {

    AuthUserModel createUser(AuthUserModel authUserModel);

    AuthUserModel updateUser(Long id, AuthUserModel authUserModel);

    Optional<AuthUserModel> getUserById(Long id);

    Optional<AuthUserModel> getUserByUsername(String username);

    List<AuthUserModel> listAllUsers();

    Page<AuthUserModel> getListPageUsers(Pageable pageable);

    void deleteUserById(Long id);

    AuthUserModel activateUserById(Long id);

    AuthUserModel deactivateUserById(Long id);
}
