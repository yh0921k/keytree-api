package io.devlabs.keytree.domains.schedule.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.devlabs.keytree.domains.schedule.domain.Schedule;
import io.devlabs.keytree.domains.schedule.domain.ScheduleRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
  @Mock ScheduleRepository scheduleRepository;

  @InjectMocks ScheduleService scheduleService;

  @Test
  @DisplayName("스케줄 생성")
  void createSchedule() {
    // given
    CreateScheduleRequest request = createScheduleRequest();
    Schedule schedule = createScheduleEntity(1L, createScheduleRequest());

    when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

    // when
    CreateScheduleResponse response = scheduleService.createSchedule(request);

    // then
    assertThat(response.getId()).isEqualTo(1L);
    assertThat(response.getStartedAt()).isEqualTo(schedule.getStartedAt());
    assertThat(response.getFinishedAt()).isEqualTo(schedule.getFinishedAt());
    assertThat(response.getTitle()).isEqualTo(schedule.getTitle());
    assertThat(response.getContents()).isEqualTo(schedule.getContents());
  }

  @Test
  @DisplayName("스케줄 리스트 조회")
  void getSchedules() {
    // given
    Schedule firstSchedule = createScheduleEntity(1L, createScheduleRequest());
    Schedule secondSchedule = createScheduleEntity(2L, createScheduleRequest());
    List<Schedule> schedules = List.of(firstSchedule, secondSchedule);

    when(scheduleRepository.findAll()).thenReturn(schedules);

    // when
    List<CreateScheduleResponse> foundSchedules = scheduleService.getSchedules();
    List<Long> scheduleIds = foundSchedules.stream().map(CreateScheduleResponse::getId).toList();

    // then
    assertThat(foundSchedules.size()).isEqualTo(schedules.size());
    assertThat(scheduleIds.contains(firstSchedule.getId())).isTrue();
    assertThat(scheduleIds.contains(secondSchedule.getId())).isTrue();
  }

  private Schedule createScheduleEntity(Long scheduleId, CreateScheduleRequest request) {
    return Schedule.builder()
        .id(scheduleId)
        .startedAt(request.getStartedAt())
        .finishedAt(request.getFinishedAt())
        .title(request.getTitle())
        .contents(request.getContents())
        .userId(1L)
        .build();
  }

  private CreateScheduleRequest createScheduleRequest() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime finishedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    CreateScheduleRequest request = new CreateScheduleRequest();
    request.setStartedAt(startedAt);
    request.setFinishedAt(finishedAt);
    request.setTitle("Title");
    request.setContents("Contents");

    return request;
  }
}
