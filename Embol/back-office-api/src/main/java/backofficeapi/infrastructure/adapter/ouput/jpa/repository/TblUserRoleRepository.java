package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserRoleRepository
 *   Descripción: Repositorio JPA para la entidad intermedia TblUsuarioRol
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblUserRoleRepository extends JpaRepository<TblUsuarioRol, Long> {

    List<TblUsuarioRol> findByUsuario(TblUsuario usuario);

    List<TblUsuarioRol> findByUsuario_Id(Long usuarioId);

    void deleteByUsuario(TblUsuario usuario);

    void deleteByUsuario_Id(Long usuarioId);
}
