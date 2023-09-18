package io.devlabs.keytree.domains.user.domain;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import io.devlabs.keytree.domains.auth.domain.UserRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("사용자 아이디")
  private Long id;

  @Column
  @NotNull
  @Comment("근무시작일시")
  private LocalDateTime startedAt;

  @Column
  @Comment("근무종료일시")
  private LocalDateTime finishedAt;

  @Column
  @NotNull
  @Comment("법인 계정 이메일 주소")
  private String email;

  @Column
  @Comment("비밀번호")
  private String password;

  @Column
  @NotNull
  @Comment("이름")
  private String name;

  @Column
  @NotNull
  @Comment("연락처")
  private String phone;

  @Column
  @Comment("주소")
  private String address;

  @Enumerated(EnumType.STRING)
  @Comment("권한")
  private UserRole userRole;

  @Column
  @Comment("법인 아이디")
  private Long companyId;

  // TODO: 추후 유효성 검증 필요
  public void modify(LocalDateTime startedAt, String phone, String address) {
    this.startedAt = startedAt;
    this.phone = phone;
    this.address = address;
  }
}
