package io.devlabs.keytree.domains.schedule.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleRequest;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.devlabs.keytree.domains.schedule.domain.Schedule;
import io.devlabs.keytree.domains.schedule.domain.ScheduleRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

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
    assertThat(response.getUserId()).isEqualTo(schedule.getUserId());
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

  @Test
  @DisplayName("스케줄 아이디로 단일 스케줄 조회")
  void getScheduleById() {
    // given
    Schedule schedule = createScheduleEntity(1L, createScheduleRequest());
    when(scheduleRepository.findById(any(Long.class))).thenReturn(Optional.of(schedule));

    // when
    CreateScheduleResponse response = scheduleService.getScheduleById(schedule.getId());

    // then
    assertThat(response.getId()).isEqualTo(schedule.getId());
    assertThat(response.getUserId()).isEqualTo(schedule.getUserId());
    assertThat(response.getStartedAt()).isEqualTo(schedule.getStartedAt());
    assertThat(response.getFinishedAt()).isEqualTo(schedule.getFinishedAt());
    assertThat(response.getTitle()).isEqualTo(schedule.getTitle());
    assertThat(response.getContents()).isEqualTo(schedule.getContents());
  }

  @Test
  @DisplayName("스케줄 아이디로 단일 스케줄 조회시 아이디가 유효하지 않으면 IllegalArgumentException 발생")
  void getScheduleByInvalidIdThrowsIllegalArgumentException() {
    // given
    Schedule schedule = createScheduleEntity(1L, createScheduleRequest());

    doReturn(Optional.empty()).when(scheduleRepository).findById(not(eq(schedule.getId())));

    // when, then
    assertThatThrownBy(() -> scheduleService.getScheduleById(schedule.getId() + 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("유효하지 않은 일정입니다.");
  }

  @Test
  @DisplayName("스케줄 아이디로 스케줄 삭제시 deleteById()가 한 번 수행됨")
  void removeScheduleByIdCallDeleteByIdOnce() {
    // when
    scheduleService.removeScheduleById(1L);

    // when, then
    verify(scheduleRepository, times(1)).deleteById(any(Long.class));
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
    request.setUserId(1L);
    request.setStartedAt(startedAt);
    request.setFinishedAt(finishedAt);
    request.setTitle("Title");
    request.setContents("Contents");

    return request;
  }
}
