package io.devlabs.keytree.domains.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.devlabs.keytree.commons.redis.RedissonUtils;
import io.devlabs.keytree.domains.auth.application.dto.SignInUserRequest;
import io.devlabs.keytree.domains.auth.infrastructure.PasswordUtils;
import io.devlabs.keytree.domains.user.application.UserService;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
  @Mock UserService userService;

  @Mock PasswordUtils passwordUtils;

  @Mock RedissonUtils redissonUtils;

  @InjectMocks AuthService authService;

  @Test
  @DisplayName("정상적인 로그인")
  void signIn() {
    // given
    SignInUserRequest request =
        SignInUserRequest.builder()
            .email("keytree@devlabs.io")
            .password("TestPassword1234!")
            .build();
    User user = createUser(request.getEmail(), request.getPassword());


    // when
    when(userService.getUserByEmail(any(String.class))).thenReturn(user);
    CreateUserResponse response = authService.signIn(request, "TestSessionId");

    // then
    verify(passwordUtils, times(1)).validatePassword(any(String.class), any(String.class));
    verify(redissonUtils, times(1)).setValue(any(String.class), any(String.class));

    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getEmail()).isEqualTo(user.getEmail());
  }

  private User createUser(String email, String password) {
    return User.builder().id(1L).email(email).password(password).build();
  }
}
