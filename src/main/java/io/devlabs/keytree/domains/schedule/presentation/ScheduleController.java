package io.devlabs.keytree.domains.schedule.presentation;

import io.devlabs.keytree.commons.annotation.HasRole;
import io.devlabs.keytree.domains.auth.domain.UserRole;
import io.devlabs.keytree.domains.schedule.application.ScheduleService;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequiredArgsConstructor
public class ScheduleController {
  private final ScheduleService scheduleService;

  @PostMapping("/schedules")
  @HasRole(role = UserRole.ADMIN)
  public CreateScheduleResponse createSchedule(@RequestBody CreateScheduleRequest request) {
    return scheduleService.createSchedule(request);
  }

  @GetMapping("/schedules")
  public List<CreateScheduleResponse> getSchedules() {
    return scheduleService.getSchedules();
  }

  @GetMapping("/schedules/{scheduleId}")
  public CreateScheduleResponse getScheduleById(@PathVariable Long scheduleId) {
    return scheduleService.getScheduleById(scheduleId);
  }

  @DeleteMapping("/schedules/{scheduleId}")
  public void removeScheduleById(@PathVariable Long scheduleId) {
    scheduleService.removeScheduleById(scheduleId);
  }
}
