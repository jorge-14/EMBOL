package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRolRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleResourceRepository
 *   Descripción: Repositorio JPA para asignaciones Rol-Recurso (TBL_ROL_RECURSO)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Repository
public interface TblRoleResourceRepository extends JpaRepository<TblRolRecurso, Long> {

       @Query("SELECT rr FROM TblRolRecurso rr WHERE rr.iIdRol.iIdRol = :rolId AND rr.deleted = false")
       List<TblRolRecurso> findByRolId(@Param("rolId") Long rolId);

       @Query("SELECT CASE WHEN COUNT(rr) > 0 THEN true ELSE false END FROM TblRolRecurso rr " +
                     "WHERE rr.iIdRol.iIdRol = :rolId AND rr.iIdRecurso.iIdRecurso = :recursoId AND rr.deleted = false")
       boolean existsByRolIdAndRecursoId(@Param("rolId") Long rolId, @Param("recursoId") Long recursoId);

       @Query("SELECT rr FROM TblRolRecurso rr " +
                     "WHERE rr.iIdRol.iIdRol = :rolId AND rr.iIdRecurso.iIdRecurso = :recursoId AND rr.deleted = false")
       Optional<TblRolRecurso> findByRolIdAndRecursoId(@Param("rolId") Long rolId, @Param("recursoId") Long recursoId);

       @Query("SELECT rr.iIdRecurso.iIdRecurso FROM TblRolRecurso rr WHERE rr.iIdRol.iIdRol = :rolId AND rr.deleted = false")
       List<Long> findGrantedResourceIdsByRolId(@Param("rolId") Long rolId);

       @Modifying
       @Query("UPDATE TblRolRecurso rr SET rr.deleted = true WHERE rr.iIdRol.iIdRol = :rolId AND rr.deleted = false")
       void softDeleteAllByRolId(@Param("rolId") Long rolId);
}
