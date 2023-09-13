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
public class CompanyAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("기업 계정 고유 아이디")
    private Long id;

    @Column
    @NotNull
    @Comment("계정 이름")
    private String name;

    @Column
    @NotNull
    @Comment("로그인 아이디")
    private String loginId;

    @Column
    @NotNull
    @Comment("로그인 비밀번호")
    private String password;

    @Column
    @NotNull
    @Comment("권한")
    private Authority authority;
}
