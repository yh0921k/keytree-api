package io.devlabs.keytree.domains.schedule.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ScheduleRepositoryTest {

  @Autowired ScheduleRepository scheduleRepository;

  @Test
  @DisplayName("일정 엔티티 저장")
  void save() {
    // given
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime finishedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Schedule schedule =
        Schedule.builder()
            .startedAt(startedAt)
            .finishedAt(finishedAt)
            .title("Title")
            .contents("Contents")
            .userId(1L)
            .build();

    // when
    Schedule savedSchedule = scheduleRepository.save(schedule);

    // then
    assertThat(savedSchedule.getId()).isGreaterThan(0L);
    assertThat(savedSchedule.getTitle()).isEqualTo(schedule.getTitle());
    assertThat(savedSchedule.getContents()).isEqualTo(schedule.getContents());
    assertThat(savedSchedule.getStartedAt()).isEqualTo(schedule.getStartedAt());
    assertThat(savedSchedule.getFinishedAt()).isEqualTo(schedule.getFinishedAt());
    assertThat(savedSchedule.getUserId()).isEqualTo(schedule.getUserId());
  }

  @Test
  @DisplayName("일정 엔티티 리스트 조회")
  void findAll() {
    // given
    Schedule firstSchedule = createSchedule();
    Schedule secondSchedule = createSchedule();
    scheduleRepository.saveAll(List.of(firstSchedule, secondSchedule));

    // when
    List<Schedule> schedules = scheduleRepository.findAll();
    List<Long> scheduleIds = schedules.stream().map(Schedule::getId).toList();

    // then
    assertThat(schedules.size()).isEqualTo(2);
    assertThat(scheduleIds.contains(1L)).isTrue();
    assertThat(scheduleIds.contains(2L)).isTrue();
  }

  @Test
  @DisplayName("일정 아이디로 일정 엔티티 조회")
  void findById() {
    // given
    Schedule schedule = createSchedule();
    Schedule savedSchedule = scheduleRepository.save(schedule);

    // when
    Optional<Schedule> foundSchedule = scheduleRepository.findById(savedSchedule.getId());

    // then
    assertThat(foundSchedule.isPresent()).isTrue();
    assertThat(foundSchedule.get()).isSameAs(savedSchedule);
    assertThat(foundSchedule.get()).isEqualTo(savedSchedule);
  }

  @Test
  @DisplayName("일정 아이디로 일정 엔티티 삭제")
  void deleteById() {
    // given
    Schedule schedule = createSchedule();
    Schedule savedSchedule = scheduleRepository.save(schedule);

    // when
    scheduleRepository.deleteById(savedSchedule.getId());
    Optional<Schedule> deletedSchedule = scheduleRepository.findById(savedSchedule.getId());

    // then
    assertThat(deletedSchedule.isEmpty()).isTrue();
  }

  private Schedule createSchedule() {
    LocalDateTime startedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime finishedAt =
        LocalDateTime.parse(
            "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    return Schedule.builder()
        .startedAt(startedAt)
        .finishedAt(finishedAt)
        .title("Title")
        .contents("Contents")
        .userId(1L)
        .build();
  }
}
