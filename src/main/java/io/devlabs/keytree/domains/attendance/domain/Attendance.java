package io.devlabs.keytree.domains.attendance.domain;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Getter
@Builder
public class Attendance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private LocalDateTime startedAt;

    @Column private LocalDateTime finishedAt;
}
