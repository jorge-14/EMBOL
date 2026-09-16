package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
