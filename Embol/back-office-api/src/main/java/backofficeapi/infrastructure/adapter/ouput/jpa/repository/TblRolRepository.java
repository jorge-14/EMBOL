package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRolRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_ROL
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblRolRepository extends JpaRepository<TblRol, Long> {

    @Query("SELECT new backofficeapi.domain.model.TblRolModel(t.iIdRol, t.sNombre) " +
            "FROM TblRol t " +
            "ORDER BY t.sNombre ASC")
    List<TblRolModel> listRole();

    @Query("SELECT tblr " +
            "FROM TblRol tblr " +
            "ORDER BY tblr.sNombre ASC")
    Page<TblRol> pageListGroup(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM TblRol t " +
            "WHERE UPPER(TRIM(t.sNombre)) = UPPER(TRIM(:nombre))")
    boolean existsByName(String nombre);
}
