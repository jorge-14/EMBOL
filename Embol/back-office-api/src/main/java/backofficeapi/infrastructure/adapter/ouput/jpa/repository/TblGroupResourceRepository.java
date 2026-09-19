package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupoRecurso;
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
 *   Código de Objeto: TblGroupResourceRepository
 *   Descripción: Repositorio JPA para asignaciones Grupo-Recurso (TBL_GRUPO_RECURSO)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Repository
public interface TblGroupResourceRepository extends JpaRepository<TblGrupoRecurso, Long> {

    @Query("SELECT gr FROM TblGrupoRecurso gr WHERE gr.iIdeGrupo.iIdGrupo = :grupoId AND gr.deleted = false")
    List<TblGrupoRecurso> findByGrupoId(@Param("grupoId") Long grupoId);

    @Query("SELECT CASE WHEN COUNT(gr) > 0 THEN true ELSE false END FROM TblGrupoRecurso gr " +
           "WHERE gr.iIdeGrupo.iIdGrupo = :grupoId AND gr.iIdRecurso.iIdRecurso = :recursoId AND gr.deleted = false")
    boolean existsByGrupoIdAndRecursoId(@Param("grupoId") Long grupoId, @Param("recursoId") Long recursoId);

    @Query("SELECT gr FROM TblGrupoRecurso gr " +
           "WHERE gr.iIdeGrupo.iIdGrupo = :grupoId AND gr.iIdRecurso.iIdRecurso = :recursoId AND gr.deleted = false")
    Optional<TblGrupoRecurso> findByGrupoIdAndRecursoId(@Param("grupoId") Long grupoId, @Param("recursoId") Long recursoId);

    @Query("SELECT gr.iIdRecurso.iIdRecurso FROM TblGrupoRecurso gr WHERE gr.iIdeGrupo.iIdGrupo = :grupoId AND gr.deleted = false")
    List<Long> findGrantedResourceIdsByGrupoId(@Param("grupoId") Long grupoId);

    @Modifying
    @Query("UPDATE TblGrupoRecurso gr SET gr.deleted = true WHERE gr.iIdeGrupo.iIdGrupo = :grupoId AND gr.deleted = false")
    void softDeleteAllByGrupoId(@Param("grupoId") Long grupoId);
}
