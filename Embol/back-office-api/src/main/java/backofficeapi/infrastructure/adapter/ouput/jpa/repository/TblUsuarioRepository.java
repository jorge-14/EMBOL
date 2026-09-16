package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioRepository
 *   Descripción: Repositorio JPA para operaciones en la tabla TBL_USUARIO (Oracle)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioRepository
 *----------------------------------------
 */

@Repository
public interface TblUsuarioRepository extends JpaRepository<TblUsuario, Long> {

    Optional<TblUsuario> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<TblUsuario> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<TblUsuario> findByDeletedFalseOrderByNameAscLastnameAsc();

    Page<TblUsuario> findByDeletedFalse(Pageable pageable);
}
