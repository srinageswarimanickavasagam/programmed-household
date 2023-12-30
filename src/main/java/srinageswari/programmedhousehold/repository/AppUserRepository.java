package srinageswari.programmedhousehold.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import srinageswari.programmedhousehold.model.AppUser;

/**
 * @author smanickavasagam
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
  Optional<AppUser> findByEmail(String email);
}
