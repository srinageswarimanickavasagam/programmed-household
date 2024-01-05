package srinageswari.programmedhousehold.coreservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.coreservice.model.AppUserEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface AppUserRepository
    extends JpaRepository<AppUserEntity, Long>, JpaSpecificationExecutor<AppUserEntity> {
  Optional<AppUserEntity> findByEmail(String email);
}
