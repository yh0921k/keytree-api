package io.devlabs.keytree.domains.schedule.domain;

import io.devlabs.keytree.commons.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Schedule extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("일정 아이디")
  private Long id;

  @Column
  @Comment("일정시작일시")
  private LocalDateTime startedAt;

  @Column
  @Comment("일정종료일시")
  private LocalDateTime finishedAt;

  @Column(length = 100)
  @Comment("일정 제목")
  private String title;

  @Lob
  @Column(length = 3000)
  @Comment("일정 내용")
  private String contents;

  @Column
  @Comment("사용자 아이디")
  private Long userId;
}
