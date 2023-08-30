package io.devlabs.keytree.domains.user.application;

import io.devlabs.keytree.domains.user.application.dto.CreateUserRequest;
import io.devlabs.keytree.domains.user.application.dto.CreateUserResponse;
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
}
