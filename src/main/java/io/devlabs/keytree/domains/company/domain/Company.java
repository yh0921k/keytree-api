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
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("기업 아이디")
    private Long id;

    @Column
    @NotNull
    @Comment("기업 이름")
    private String name;

    @Column
    @NotNull
    @Comment("기업 연락처")
    private String phone;

    @Column
    @Comment("기업 주소")
    private String address;
}
