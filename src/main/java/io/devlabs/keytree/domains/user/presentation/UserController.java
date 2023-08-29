package io.devlabs.keytree.domains.user.presentation;

import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/users")
  public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request);
  }
}
