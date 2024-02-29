package srinageswari.programmedhousehold.backend.service.appuser;

import java.util.Optional;
import srinageswari.programmedhousehold.backend.model.AppUserEntity;

/**
 * @author smanickavasagam
 */
public interface IAppUserService {
  Optional<AppUserEntity> findByEmail(String email);

  void save(AppUserEntity user);
}
