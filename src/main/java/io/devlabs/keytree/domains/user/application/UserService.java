package io.devlabs.keytree.domains.user.application;

import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
import io.devlabs.keytree.domains.user.application.dto.ModifyUserRequest;
import io.devlabs.keytree.domains.user.domain.User;
import io.devlabs.keytree.domains.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public CreateUserResponse createUser(CreateUserRequest request) {
    User user =
        User.builder()
            .startedAt(request.getStartedAt())
            .email(request.getEmail())
            .name(request.getName())
            .phone(request.getPhone())
            .build();

    User savedUser = userRepository.save(user);

    return CreateUserResponse.builder()
        .id(savedUser.getId())
        .startedAt(savedUser.getStartedAt())
        .name(savedUser.getName())
        .phone(savedUser.getPhone())
        .email(savedUser.getEmail())
        .address(savedUser.getAddress())
        .build();
  }

  @Transactional
  public CreateUserResponse modifyUser(Long userId, ModifyUserRequest modifyUserRequest) {
    // TODO: 단일 사용자 조회 로직 함수 분리 필요
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 요청입니다."));

    user.modify(
        modifyUserRequest.getStartedAt(),
        modifyUserRequest.getPhone(),
        modifyUserRequest.getAddress());

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
