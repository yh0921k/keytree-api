package io.devlabs.keytree.domains.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startedAt;

  private String email;
  private String name;
  private String phone;
}
