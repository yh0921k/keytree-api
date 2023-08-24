package io.devlabs.keytree.domains.schedule.domain;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Schedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private LocalDateTime startedAt;

  @Column private LocalDateTime finishedAt;

  @Column(length = 100)
  private String title;

  @Lob
  @Column(length = 3000)
  private String contents;

  @Column
  private Long userId;
}
