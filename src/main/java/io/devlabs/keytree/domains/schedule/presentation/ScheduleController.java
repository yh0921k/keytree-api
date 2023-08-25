package io.devlabs.keytree.domains.schedule.presentation;

import io.devlabs.keytree.domains.schedule.application.ScheduleService;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class ScheduleController {
  private final ScheduleService scheduleService;

  @PostMapping("/schedules")
  public CreateScheduleResponse createSchedule(@RequestBody CreateScheduleRequest request) {
    return scheduleService.createSchedule(request);
  }

  @GetMapping("/schedules")
  public List<CreateScheduleResponse> getSchedules() {
    return scheduleService.getSchedules();
  }
}
