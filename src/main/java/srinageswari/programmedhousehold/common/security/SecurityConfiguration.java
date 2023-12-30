package srinageswari.programmedhousehold.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author smanickavasagam
 */
@ConditionalOnProperty(prefix = "app.security", name = "enabled", havingValue = "true")
@Profile(value = {"dev", "prod"})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
  @Autowired OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> {
              auth.anyRequest().authenticated();
            })
        .oauth2Login(
            oath2 -> {
              oath2.permitAll();
              oath2.successHandler(oAuth2LoginSuccessHandler);
            })
        .build();
  }
}
