package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblRoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRepositoryPort
 *   Descripción: Puerto de salida para persistencia de Role (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

public interface TblRoleRepositoryPort {

    TblRoleModel saveRole(TblRoleModel tblRolModel);
    List<TblRoleModel> listRole();
    Optional<TblRoleModel> findById(Long id);

    Page<TblRoleModel> getPageListRol(Pageable pageable);

    boolean existsRolByName(String nombre);
}
