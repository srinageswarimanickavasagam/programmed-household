package srinageswari.programmedhousehold.coreservice.server.service.appuser;

import java.util.Optional;
import srinageswari.programmedhousehold.coreservice.server.model.AppUserEntity;

/**
 * @author smanickavasagam
 */
public interface IAppUserService {
  Optional<AppUserEntity> findByEmail(String email);

  void save(AppUserEntity user);
}
