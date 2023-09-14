package io.devlabs.keytree.domains.auth.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInUserRequest {
  private String email;
  private String password;
}
