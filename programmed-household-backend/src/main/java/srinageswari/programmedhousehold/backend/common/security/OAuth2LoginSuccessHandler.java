package srinageswari.programmedhousehold.backend.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import srinageswari.programmedhousehold.backend.model.AppUserEntity;
import srinageswari.programmedhousehold.backend.service.appuser.IAppUserService;

/**
 * @author smanickavasagam
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final IAppUserService appUserService;

  @Value("${app.frontend.url}")
  private String frontendUrl;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    OAuth2AuthenticationToken oAuth2AuthenticationToken =
        (OAuth2AuthenticationToken) authentication;
    DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = principal.getAttributes();
    String email = attributes.getOrDefault("email", "").toString();
    String name = attributes.getOrDefault("name", "").toString();
    appUserService
        .findByEmail(email)
        .ifPresentOrElse(
            user -> {
              DefaultOAuth2User newUser =
                  new DefaultOAuth2User(
                      List.of(new SimpleGrantedAuthority("ADMIN")), attributes, "email");
              Authentication securityAuth =
                  new OAuth2AuthenticationToken(
                      newUser,
                      List.of(new SimpleGrantedAuthority("ADMIN")),
                      oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
              SecurityContextHolder.getContext().setAuthentication(securityAuth);
            },
            () -> {
              AppUserEntity appUserEntity = new AppUserEntity();
              appUserEntity.setEmail(email);
              appUserEntity.setName(name);
              appUserEntity.setProvider(
                  oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
              appUserService.save(appUserEntity);
              DefaultOAuth2User newUser =
                  new DefaultOAuth2User(
                      List.of(new SimpleGrantedAuthority("ADMIN")), attributes, "email");
              Authentication securityAuth =
                  new OAuth2AuthenticationToken(
                      newUser,
                      List.of(new SimpleGrantedAuthority("ADMIN")),
                      oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
              SecurityContextHolder.getContext().setAuthentication(securityAuth);
            });
    this.setAlwaysUseDefaultTargetUrl(true);
    this.setDefaultTargetUrl(frontendUrl);
    super.onAuthenticationSuccess(request, response, authentication);
  }
}
