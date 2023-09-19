package io.devlabs.keytree.domains.auth.presentation;

import io.devlabs.keytree.domains.auth.application.AuthService;
import io.devlabs.keytree.domains.auth.application.dto.SignInUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signIn")
  public ResponseEntity<CreateUserResponse> signIn(
      HttpServletRequest request, @RequestBody SignInUserRequest signInUserRequest) {
    request.getSession().invalidate();

    HttpSession createdSession = request.getSession(true);
    createdSession.setAttribute("email", signInUserRequest.getEmail());
    createdSession.setMaxInactiveInterval(3600);

    CreateUserResponse responseData = authService.signIn(signInUserRequest, createdSession.getId());
    return ResponseEntity.ok(responseData);
  }

  @PostMapping("/logout")
  public String logout (HttpServletRequest request) {
    return authService.logout(request);
  }
}
