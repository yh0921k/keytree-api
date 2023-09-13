package io.devlabs.keytree.domains.schedule.application;

import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.devlabs.keytree.domains.schedule.domain.Schedule;
import io.devlabs.keytree.domains.schedule.domain.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;

  @Transactional
  public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
    Schedule schedule =
        Schedule.builder()
            .userId(request.getUserId())
            .startedAt(request.getStartedAt())
            .finishedAt(request.getFinishedAt())
            .title(request.getTitle())
            .contents(request.getContents())
            .build();

    Schedule savedSchedule = scheduleRepository.save(schedule);

    return CreateScheduleResponse.builder()
        .id(savedSchedule.getId())
        .userId(savedSchedule.getUserId())
        .startedAt(savedSchedule.getStartedAt())
        .finishedAt(savedSchedule.getFinishedAt())
        .title(savedSchedule.getTitle())
        .contents(savedSchedule.getContents())
        .build();
  }

  @Transactional(readOnly = true)
  public List<CreateScheduleResponse> getSchedules() {
    List<Schedule> schedules = scheduleRepository.findAll();

    return schedules.stream()
        .map(
            schedule ->
                CreateScheduleResponse.builder()
                    .id(schedule.getId())
                    .userId(schedule.getUserId())
                    .startedAt(schedule.getStartedAt())
                    .finishedAt(schedule.getFinishedAt())
                    .title(schedule.getTitle())
                    .contents(schedule.getContents())
                    .build())
        .toList();
  }

  @Transactional(readOnly = true)
  public CreateScheduleResponse getScheduleById(Long id) {
    Schedule schedule = scheduleRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 일정입니다."));

    return CreateScheduleResponse.builder()
        .id(schedule.getId())
        .userId(schedule.getUserId())
        .startedAt(schedule.getStartedAt())
        .finishedAt(schedule.getFinishedAt())
        .title(schedule.getTitle())
        .contents(schedule.getContents())
        .build();
  }

  @Transactional
  public void removeScheduleById(Long id) {
    scheduleRepository.deleteById(id);
  }
}
