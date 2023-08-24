package io.devlabs.keytree.domains.schedule.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ScheduleRepositoryTest {

  @Autowired
  ScheduleRepository scheduleRepository;

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

    Schedule schedule = Schedule.builder()
        .startedAt(startedAt)
        .finishedAt(finishedAt)
        .title("Title")
        .contents("Contents")
        .userId(1L)
        .build();

    // when
    Schedule savedSchedule = scheduleRepository.save(schedule);

    // then
    assertThat(savedSchedule.getId()).isEqualTo(1L);
    assertThat(savedSchedule.getTitle()).isEqualTo(schedule.getTitle());
    assertThat(savedSchedule.getContents()).isEqualTo(schedule.getContents());
    assertThat(savedSchedule.getStartedAt()).isEqualTo(schedule.getStartedAt());
    assertThat(savedSchedule.getFinishedAt()).isEqualTo(schedule.getFinishedAt());
    assertThat(savedSchedule.getUserId()).isEqualTo(schedule.getUserId());
  }
}
