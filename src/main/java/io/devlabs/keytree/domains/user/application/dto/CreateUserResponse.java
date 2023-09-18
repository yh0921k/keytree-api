package io.devlabs.keytree.domains.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.devlabs.keytree.domains.auth.domain.UserRole;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateUserResponse {
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startedAt;

  private String email;
  private String name;
  private String phone;
  private String address;
  private UserRole userRole;
}
