package io.devlabs.keytree.domains.company.domain;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("관리자 아이디")
    private Long id;

    @Column
    @NotNull
    @Comment("이름")
    private String name;

    @Column
    @NotNull
    @Comment("이메일")
    private String email;

    @Column
    @NotNull
    @Comment("비밀번호")
    private String password;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    @Comment("관리자 권한")
    private UserRole userRole;
}
