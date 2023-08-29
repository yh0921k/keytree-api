package io.devlabs.keytree.domains.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.domain.User;
import io.devlabs.keytree.domains.user.domain.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;

  @InjectMocks UserService userService;

  @Test
  @DisplayName("사용자 생성")
  void createUser() {
    // given
    CreateUserRequest request = createUserRequest();
    User user = createUser(1L, request);
    when(userRepository.save(any(User.class))).thenReturn(user);

    // when
    CreateUserResponse response = userService.createUser(request);

    // then
    assertThat(response.getId()).isGreaterThan(1L);
    assertThat(response.getStartedAt()).isEqualTo(user.getStartedAt());
    assertThat(response.getName()).isEqualTo(user.getName());
    assertThat(response.getPhone()).isEqualTo(user.getPhone());
    assertThat(response.getEmail()).isEqualTo(user.getEmail());
  }

  private User createUser(Long id, CreateUserRequest request) {
    String invalidValue = "KeyTree";
    LocalDateTime now = LocalDateTime.now();


    return User.builder()
        .id(id)
        .startedAt(request.getStartedAt())
        .finishedAt(now.plusYears(1))
        .email(request.getEmail())
        .password(invalidValue)
        .name(request.getName())
        .phone(request.getPhone())
        .address(invalidValue)
        .companyId(0L)
        .build();
  }


  private CreateUserRequest createUserRequest() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    CreateUserRequest request = new CreateUserRequest();
    request.setStartedAt(startedAt);
    request.setName("KeyTree");
    request.setPhone("01011112222");
    request.setEmail("KeyTree");

    return request;
  }
}
