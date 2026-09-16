package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRegional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalRepository
 *   Descripción: Repositorio JPA para operaciones en tabla TBL_REGIONAL
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface TblRegionalRepository extends JpaRepository<TblRegional, Long> {

    Optional<TblRegional> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
