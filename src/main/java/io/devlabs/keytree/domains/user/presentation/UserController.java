package io.devlabs.keytree.domains.user.presentation;

import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.application.dto.ModifyUserRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/users")
  public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request);
  }

  @PatchMapping("/users/{userId}")
  public CreateUserResponse modifyUser(
      @PathVariable Long userId, @RequestBody ModifyUserRequest request) {
    return userService.modifyUser(userId, request);
  }

  @GetMapping("/users/{userId}")
  public CreateUserResponse getUserById(@PathVariable Long userId) {
    return userService.getUserById(userId);
  }

  @GetMapping("/users")
  public List<CreateUserResponse> getUserById() {
    return userService.getUsers();
  }
}
