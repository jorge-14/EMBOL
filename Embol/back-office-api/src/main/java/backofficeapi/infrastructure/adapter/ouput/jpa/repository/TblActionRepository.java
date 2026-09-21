package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblActionRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_ACCION
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblActionRepository extends JpaRepository<TblAccion, Long> {

    @Query("SELECT a " +
            "FROM TblAccion a " +
            "WHERE UPPER(TRIM(a.sCodigo)) = UPPER(TRIM(:codigo)) AND a.deleted = false")
    Optional<TblAccion> actionFindByCode(@Param("code") String code);

    @Query("SELECT a " +
            "FROM TblAccion a " +
            "WHERE UPPER(TRIM(a.sNombre)) = UPPER(TRIM(:nombre)) AND a.deleted = false")
    Optional<TblAccion> findByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM TblAccion a " +
            "WHERE UPPER(TRIM(a.sCodigo)) = UPPER(TRIM(:codigo)) AND a.deleted = false")
    boolean existsByCode(@Param("code") String code);
}
