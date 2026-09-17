package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblRolModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface TblRolRepositoryPort {

    TblRolModel saveRole(TblRolModel tblRolModel);
    List<TblRolModel> listRole();
    Optional<TblRolModel> findById(Long id);

    Page<TblRolModel> getPageListRol(Pageable pageable);

    boolean existsRolByName(String nombre);

}
