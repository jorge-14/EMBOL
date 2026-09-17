package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioGrupoRepository
 *   Descripción: Repositorio JPA para la entidad intermedia TblUsuarioGrupo
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblUsuarioGrupoRepository extends JpaRepository<TblUsuarioGrupo, Long> {

    List<TblUsuarioGrupo> findByUsuario(TblUsuario usuario);

    List<TblUsuarioGrupo> findByUsuario_Id(Long usuarioId);

    void deleteByUsuario(TblUsuario usuario);

    void deleteByUsuario_Id(Long usuarioId);
}
