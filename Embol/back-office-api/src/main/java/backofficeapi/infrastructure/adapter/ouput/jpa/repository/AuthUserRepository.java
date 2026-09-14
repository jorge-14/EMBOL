package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    @Query("SELECT au " +
            "FROM AuthUser au " +
            "WHERE au.entraId = :entraId")
    Optional<AuthUser> findByEntraId(@Param("entraId") String entraId);

    @Query("SELECT CASE WHEN COUNT(au) > 0 THEN TRUE ELSE FALSE END " +
            "FROM AuthUser au " +
            "WHERE au.entraId = :entraId")
    Boolean existsByEntraId(@Param("entraId") String entraId);

    @Query("SELECT au " +
            "FROM AuthUser au " +
            "ORDER BY au.fatherLastname, au.motherLastname, au.name ")
    List<AuthUser> findAllUser();
}
