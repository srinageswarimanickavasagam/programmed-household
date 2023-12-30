package srinageswari.programmedhousehold.service.appuser;

import java.util.Optional;
import srinageswari.programmedhousehold.model.AppUser;

/**
 * @author smanickavasagam
 */
public interface IAppUserService {
  Optional<AppUser> findByEmail(String email);

  void save(AppUser user);
}
