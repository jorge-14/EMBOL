package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblUserUseCase
 *   Descripción: Puerto de entrada para casos de uso de gestión de TblUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Métodos activate/deactivate y roles/grupos
 *----------------------------------------
 */

public interface CrudTblUserUseCase {

    TblUserModel createUser(TblUserModel tblUserModel);

    TblUserModel updateUser(Long id, TblUserModel tblUserModel);

    Optional<TblUserModel> getUserById(Long id);

    Optional<TblUserModel> getUserByUsername(String username);

    List<TblUserModel> listAllUsers();

    Page<TblUserModel> getListPageUsers(Pageable pageable);

    void deleteUserById(Long id);

    TblUserModel activateUserById(Long id);

    TblUserModel deactivateUserById(Long id);
}
