package srinageswari.programmedhousehold.service.appuser;

import java.util.Optional;
import srinageswari.programmedhousehold.model.AppUserEntity;

/**
 * @author smanickavasagam
 */
public interface IAppUserService {
  Optional<AppUserEntity> findByEmail(String email);

  void save(AppUserEntity user);
}
