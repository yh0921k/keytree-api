package io.devlabs.keytree.domains.user.application.dto;

import java.time.LocalDateTime;

public class GenerateModifyUserRequest {
  public static ModifyUserRequest generate() {
    return ModifyUserRequest.builder()
        .startedAt(LocalDateTime.now())
        .phone("010-4321-4321")
        .address("Modified Address")
        .build();
  }
}
