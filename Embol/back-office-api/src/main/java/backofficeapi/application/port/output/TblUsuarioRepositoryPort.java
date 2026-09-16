package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblUsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioRepositoryPort
 *   Descripción: Puerto de salida para persistencia de TblUsuario (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioRepositoryPort y paginación
 *----------------------------------------
 */

public interface TblUsuarioRepositoryPort {

    TblUsuarioModel save(TblUsuarioModel tblUsuarioModel);

    Optional<TblUsuarioModel> findById(Long id);

    Optional<TblUsuarioModel> findByUsername(String username);

    Optional<TblUsuarioModel> findByEmail(String email);

    List<TblUsuarioModel> findAll();

    Page<TblUsuarioModel> findPage(Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
