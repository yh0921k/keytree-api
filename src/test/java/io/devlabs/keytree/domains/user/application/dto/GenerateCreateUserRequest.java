package io.devlabs.keytree.domains.user.application.dto;

import io.devlabs.keytree.domains.auth.domain.UserRole;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateCreateUserRequest {

  public static CreateUserRequest generate() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    return CreateUserRequest.builder()
        .startedAt(startedAt)
        .name("KeyTree")
        .phone("010-1111-2222")
        .email("keytree")
        .userRole(UserRole.USER)
        .build();
  }
}
