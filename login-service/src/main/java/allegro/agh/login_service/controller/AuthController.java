package allegro.agh.login_service.controller;

import allegro.agh.login_service.model.LoginMessage;
import allegro.agh.login_service.model.LoginRequest;
import allegro.agh.login_service.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;

  public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/api/token")
  public LoginMessage token(@RequestBody LoginRequest userLogin) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));

    return new LoginMessage(tokenService.generateToken(authentication));
  }
}
