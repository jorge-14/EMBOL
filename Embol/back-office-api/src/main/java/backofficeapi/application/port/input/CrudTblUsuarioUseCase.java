package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblUsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblUsuarioUseCase
 *   Descripción: Puerto de entrada (caso de uso) para operaciones CRUD de TblUsuario (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura CrudTblUsuarioUseCase y paginación
 *----------------------------------------
 */

public interface CrudTblUsuarioUseCase {

    TblUsuarioModel createUser(TblUsuarioModel tblUsuarioModel);

    TblUsuarioModel updateUser(Long id, TblUsuarioModel tblUsuarioModel);

    Optional<TblUsuarioModel> getUserById(Long id);

    Optional<TblUsuarioModel> getUserByUsername(String username);

    List<TblUsuarioModel> listAllUsers();

    Page<TblUsuarioModel> getListPageUsers(Pageable pageable);

    void deleteUserById(Long id);

    TblUsuarioModel activateUserById(Long id);

    TblUsuarioModel deactivateUserById(Long id);
}
