package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_RECURSO
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Repository
public interface TblResourceRepository extends JpaRepository<TblRecurso, Long> {

    @Query("SELECT r FROM TblRecurso r WHERE UPPER(TRIM(r.sNombre)) = UPPER(TRIM(:nombre)) AND r.deleted = false")
    Optional<TblRecurso> findBySNombre(@Param("nombre") String nombre);

    @Query("SELECT r FROM TblRecurso r WHERE r.iIdRecursoPadre IS NULL AND r.deleted = false ORDER BY r.iIdRecurso ASC")
    List<TblRecurso> findAllParents();

    @Query("SELECT r FROM TblRecurso r WHERE r.iIdRecursoPadre.iIdRecurso = :padreId AND r.deleted = false ORDER BY r.iIdRecurso ASC")
    List<TblRecurso> findByPadreId(@Param("padreId") Long padreId);
}
