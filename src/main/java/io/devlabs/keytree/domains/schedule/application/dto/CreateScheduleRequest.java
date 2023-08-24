package io.devlabs.keytree.domains.schedule.application.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateScheduleRequest {
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private String title;
  private String contents;
}
