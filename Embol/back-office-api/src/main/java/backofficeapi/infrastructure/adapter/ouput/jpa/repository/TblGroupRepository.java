package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblGroupRepository extends JpaRepository<TblGrupo, Long> {

    @Query("SELECT tblg " +
            "FROM TblGrupo tblg " +
            "WHERE tblg.deleted = false " +
            "ORDER BY tblg.sNombre ASC")
    Page<TblGrupo> pageListGroup(Pageable pageable);
}
