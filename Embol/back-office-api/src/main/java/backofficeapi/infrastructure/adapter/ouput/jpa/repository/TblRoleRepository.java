package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_ROL
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblRoleRepository extends JpaRepository<TblRol, Long> {

    @Query("SELECT new backofficeapi.domain.model.TblRoleModel(t.iIdRol, t.sNombre) " +
            "FROM TblRol t " +
            "WHERE t.deleted = false " +
            "ORDER BY t.sNombre ASC")
    List<TblRoleModel> listRole();

    @Query("SELECT tblr " +
            "FROM TblRol tblr " +
            "WHERE tblr.deleted = false " +
            "ORDER BY tblr.sNombre ASC")
    Page<TblRol> pageListGroup(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM TblRol t " +
            "WHERE UPPER(TRIM(t.sNombre)) = UPPER(TRIM(:nombre)) AND t.deleted = false")
    boolean existsByName(String nombre);

    @Query("SELECT r " +
            "FROM TblRol r " +
            "WHERE r.iIdRol = :id AND r.deleted = false")
    Optional<TblRol> findActiveById(@Param("id") Long id);

}
