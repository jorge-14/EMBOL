package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecursoAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceActionRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_RECURSO_ACCION
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Repository
public interface TblResourceActionRepository extends JpaRepository<TblRecursoAccion, Long> {

    @Query("SELECT CASE WHEN COUNT(ra) > 0 THEN true ELSE false END FROM TblRecursoAccion ra " +
           "WHERE ra.iIdRecurso = :recurso AND ra.iIdAccion = :accion AND ra.deleted = false")
    boolean existsByIIdRecursoAndIIdAccion(@Param("recurso") TblRecurso recurso, @Param("accion") TblAccion accion);

    @Query("SELECT ra FROM TblRecursoAccion ra WHERE ra.iIdRecurso = :recurso AND ra.iIdAccion = :accion AND ra.deleted = false")
    Optional<TblRecursoAccion> findByRecursoAndAccion(@Param("recurso") TblRecurso recurso, @Param("accion") TblAccion accion);

    @Query("SELECT ra FROM TblRecursoAccion ra WHERE ra.iIdRecurso.iIdRecurso = :recursoId AND ra.deleted = false")
    List<TblRecursoAccion> findByRecursoId(@Param("recursoId") Long recursoId);
}
