package io.devlabs.keytree.domains.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.application.dto.ModifyUserRequest;
import io.devlabs.keytree.domains.user.domain.User;
import io.devlabs.keytree.domains.user.domain.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getStartedAt()).isEqualTo(user.getStartedAt());
    assertThat(response.getName()).isEqualTo(user.getName());
    assertThat(response.getPhone()).isEqualTo(user.getPhone());
    assertThat(response.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  @DisplayName("사용자 수정")
  void modifyUser() {
    // given
    User user = createUser(1L, createUserRequest());
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

    // when
    ModifyUserRequest modifyUserRequest = createModifyUserRequest();
    CreateUserResponse response = userService.modifyUser(user.getId(), modifyUserRequest);

    // then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getStartedAt()).isEqualTo(modifyUserRequest.getStartedAt());
    assertThat(response.getPhone()).isEqualTo(modifyUserRequest.getPhone());
    assertThat(response.getAddress()).isEqualTo(modifyUserRequest.getAddress());
  }

  @Test
  @DisplayName("사용자 아이디로 단일 사용자 조회")
  void getUserById() {
    // given
    User user = createUser(1L, createUserRequest());
    when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

    // when
    CreateUserResponse response = userService.getUserById(user.getId());

    // then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getStartedAt()).isEqualTo(user.getStartedAt());
    assertThat(response.getPhone()).isEqualTo(user.getPhone());
    assertThat(response.getAddress()).isEqualTo(user.getAddress());
  }

  @Test
  @DisplayName("사용자 아이디로 단일 사용자 조회시 아이디가 유효하지 않으면 IllegalArgumentException 발생")
  void getUserByInvalidIdThrowsIllegalArgumentException() {
    // given
    User user = createUser(1L, createUserRequest());

    doReturn(Optional.empty()).when(userRepository).findById(not(eq(user.getId())));

    // when, then
    assertThatThrownBy(() -> userService.getUserById(user.getId() + 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("존재하지 않는 사용자입니다.");
  }

  @Test
  @DisplayName("사용자 이메일로 단일 사용자 조회")
  void getUserByEmail() {
    // given
    User user = createUser(1L, createUserRequest());
    when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

    // when
    User foundUser = userService.getUserByEmail(user.getEmail());

    // then
    assertThat(foundUser.getId()).isEqualTo(1L);
    assertThat(foundUser.getStartedAt()).isEqualTo(user.getStartedAt());
    assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    assertThat(foundUser.getPhone()).isEqualTo(user.getPhone());
    assertThat(foundUser.getAddress()).isEqualTo(user.getAddress());
  }

  @Test
  @DisplayName("사용자 이메일로 사용자 엔티티 조회가 불가능한 경우 IllegalArgumentException 발생")
  void getUserByInvalidEmailThrowsIllegalArgumentException() {
    // given
    User user = createUser(1L, createUserRequest());

    doReturn(Optional.empty()).when(userRepository).findByEmail(not(eq(user.getEmail())));

    // when, then
    assertThatThrownBy(() -> userService.getUserByEmail("invalid_email@gmail.com"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("존재하지 않는 사용자입니다.");
  }

  @Test
  @DisplayName("사용자 리스트 조회")
  void getSchedules() {
    // given
    User firstUser = createUser(1L, createUserRequest());
    User secondUser = createUser(2L, createUserRequest());
    List<User> users = List.of(firstUser, secondUser);

    when(userRepository.findAll()).thenReturn(users);

    // when
    List<CreateUserResponse> foundUsers = userService.getUsers();
    List<Long> userIds = foundUsers.stream().map(CreateUserResponse::getId).toList();

    // then
    assertThat(foundUsers.size()).isEqualTo(users.size());
    assertThat(userIds.contains(firstUser.getId())).isTrue();
    assertThat(userIds.contains(secondUser.getId())).isTrue();
  }

  private ModifyUserRequest createModifyUserRequest() {
    ModifyUserRequest request = new ModifyUserRequest();
    request.setStartedAt(LocalDateTime.now());
    request.setPhone("010-4321-4321");
    request.setAddress("Modified Address");

    return request;
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
    request.setEmail("keytree@gmail.com");

    return request;
  }
}
