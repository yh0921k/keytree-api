package io.devlabs.keytree.domains.user;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private LocalDateTime startedAt;

  @Column private LocalDateTime finishedAt;

  @Column private String email;
  @Column private String password;
  @Column private String name;
  @Column private String phone;
  @Column private String address;

  @Column private Long companyId;
}
