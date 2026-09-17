package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserRepositoryPort
 *   Descripción: Puerto de salida para persistencia de TblUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUserRepositoryPort
 *----------------------------------------
 */

public interface TblUserRepositoryPort {

    TblUserModel save(TblUserModel tblUserModel);

    Optional<TblUserModel> findById(Long id);

    Optional<TblUserModel> findByUsername(String username);

    Optional<TblUserModel> findByEmail(String email);

    List<TblUserModel> findAll();

    Page<TblUserModel> findPage(Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
