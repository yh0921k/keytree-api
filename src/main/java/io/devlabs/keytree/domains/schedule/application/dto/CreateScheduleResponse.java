package io.devlabs.keytree.domains.schedule.application.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateScheduleResponse {
  private Long id;
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private String title;
  private String contents;
}
