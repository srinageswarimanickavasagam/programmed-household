package srinageswari.programmedhousehold.service.appuser;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import srinageswari.programmedhousehold.model.AppUserEntity;
import srinageswari.programmedhousehold.repository.AppUserRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for saving/querying user details
 */
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements IAppUserService {

  private final AppUserRepository appUserRepository;

  @Override
  public Optional<AppUserEntity> findByEmail(String email) {
    return appUserRepository.findByEmail(email);
  }

  @Override
  public void save(AppUserEntity user) {
    appUserRepository.save(user);
  }

  public AppUserEntity getCurrentLoggedInUser() {
    return findByEmail(
            String.valueOf(
                ((DefaultOAuth2User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getAttributes()
                    .get("email")))
        .get();
  }
}
