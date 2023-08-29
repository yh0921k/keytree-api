package io.devlabs.keytree.domains.attendance.domain;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Getter
@Builder
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("출석 아이디")
    private Long id;

    @Column
    @NotNull
    @Comment("사용자 아이디")
    private Long userId;

    @Column
    @NotNull
    @Comment("출근 일시")
    private LocalDateTime startedAt;

    @Column
    @NotNull
    @Comment("퇴근 일시")
    private LocalDateTime finishedAt;
}
