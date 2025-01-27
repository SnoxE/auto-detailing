package allegro.agh.login_service.config;

import static org.springframework.security.config.Customizer.withDefaults;

import allegro.agh.login_service.database.user.sql.UserSqlService;
import allegro.agh.login_service.database.user.sql.model.UserLoginSqlRow;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final RsaKeyProperties rsaKeys;

  public SecurityConfig(RsaKeyProperties rsaKeys) {
    this.rsaKeys = rsaKeys;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public UserDetailsService user(UserSqlService userSqlService) {
    return (username) -> {
      UserLoginSqlRow userLoginSqlRow = userSqlService.getUserByEmail(username);
      if (userLoginSqlRow == null) throw new UsernameNotFoundException("email not found");
      return User.withUsername(userLoginSqlRow.email())
          .password(userLoginSqlRow.password())
          .authorities("read")
          .build();
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/token",
                        "/api/users/register",
                        "/api/users/email",
                        "/api/users/problem",
                        "/swagger-ui/**",
                        "/v3/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()))
        .exceptionHandling(
            (ex) ->
                ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
        .build();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8081"));
    configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
