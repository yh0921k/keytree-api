package io.devlabs.keytree.domains.auth.application;

import io.devlabs.keytree.commons.redis.RedissonUtils;
import io.devlabs.keytree.domains.auth.application.dto.SignInUserRequest;
import io.devlabs.keytree.domains.auth.domain.Email;
import io.devlabs.keytree.domains.auth.domain.Password;
import io.devlabs.keytree.domains.auth.infrastructure.PasswordUtils;
import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final PasswordUtils passwordUtils;
  private final RedissonUtils redissonUtils;

  @Transactional(readOnly = true)
  public CreateUserResponse signIn(SignInUserRequest request, String sessionId) {
    Email email = new Email(request.getEmail());
    Password password = new Password(request.getPassword());

    User user = userService.getUserByEmail(request.getEmail());
    passwordUtils.validatePassword(password.getValue(), user.getPassword());
    redissonUtils.setValue(email.getValue(), sessionId);

    return CreateUserResponse.builder()
        .id(user.getId())
        .startedAt(user.getStartedAt())
        .name(user.getName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .address(user.getAddress())
        .build();
  }
}
