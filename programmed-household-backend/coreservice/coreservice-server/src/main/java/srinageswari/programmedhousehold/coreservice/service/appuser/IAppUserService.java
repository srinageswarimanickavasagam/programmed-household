package srinageswari.programmedhousehold.coreservice.service.appuser;

import java.util.Optional;
import srinageswari.programmedhousehold.coreservice.model.AppUserEntity;

/**
 * @author smanickavasagam
 */
public interface IAppUserService {
  Optional<AppUserEntity> findByEmail(String email);

  void save(AppUserEntity user);
}
