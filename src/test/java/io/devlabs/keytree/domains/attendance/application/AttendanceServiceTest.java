package io.devlabs.keytree.domains.attendance.application;

import io.devlabs.keytree.domains.attendance.application.application.AttendanceService;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import io.devlabs.keytree.domains.attendance.domain.Attendance;
import io.devlabs.keytree.domains.attendance.domain.AttendanceRepository;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.devlabs.keytree.domains.schedule.domain.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @Mock
    AttendanceRepository attendanceRepository;

    @InjectMocks
    AttendanceService attendanceService;

    @Test
    @DisplayName("출근 생성")
    void createStartAttendance() {
        // given
        CreateStartAttendanceRequest request = createStartAttendanceRequest();
        Attendance attendance = createStartAttendanceEntity(1L, createStartAttendanceRequest());

        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);

        // when
        CreateStartAttendanceResponse response = attendanceService.createStartAttendance(request);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUserId()).isEqualTo(attendance.getUserId());
        assertThat(response.getStartedAt()).isEqualTo(attendance.getStartedAt());
    }

    @Test
    @DisplayName("출근 목록 조회")
    void getAttendance() {
        // given
        Attendance firstAttendance = createStartAttendanceEntity(1L, createStartAttendanceRequest());
        Attendance secondAttendance = createStartAttendanceEntity(2L, createStartAttendanceRequest());
        List<Attendance> attendances = List.of(firstAttendance, secondAttendance);

        when(attendanceRepository.findAll()).thenReturn(attendances);

        // when
        List<Attendance> foundAttendances = attendanceService.getAttendances();
        List<Long> attendanceIds = foundAttendances.stream().map(Attendance::getId).toList();

        // then
        assertThat(foundAttendances.size()).isEqualTo(attendances.size());
        assertThat(attendanceIds.contains(firstAttendance.getId())).isTrue();
        assertThat(attendanceIds.contains(secondAttendance.getId())).isTrue();
    }

    private Attendance createStartAttendanceEntity(Long attendanceId, CreateStartAttendanceRequest request) {
        return Attendance.builder()
                .id(attendanceId)
                .startedAt(request.getStartedAt())
                .userId(request.getUserId())
                .build();
    }
    private CreateStartAttendanceRequest createStartAttendanceRequest() {
        LocalDateTime startedAt =
                LocalDateTime.parse(
                        "2023-08-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        CreateStartAttendanceRequest request = new CreateStartAttendanceRequest();
        request.setStartedAt(startedAt);

        return request;
    }
}
