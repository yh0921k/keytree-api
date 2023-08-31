package io.devlabs.keytree.domains.attendance.application.application;

import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import io.devlabs.keytree.domains.attendance.domain.Attendance;
import io.devlabs.keytree.domains.attendance.domain.AttendanceRepository;
import io.devlabs.keytree.domains.schedule.application.dto.CreateScheduleResponse;
import io.devlabs.keytree.domains.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public CreateStartAttendanceResponse createStartAttendance(CreateStartAttendanceRequest request) {
        Attendance attendance =
                Attendance.builder()
                        .userId(request.getUserId())
                        .startedAt(request.getStartedAt())
                        .build();

        Attendance savedStartAttendance = attendanceRepository.save(attendance);

        return CreateStartAttendanceResponse.builder()
                .id(savedStartAttendance.getId())
                .userId(savedStartAttendance.getUserId())
                .startedAt(savedStartAttendance.getStartedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances.stream()
                .map(
                        attendance ->
                                Attendance.builder()
                                        .id(attendance.getId())
                                        .userId(attendance.getUserId())
                                        .startedAt(attendance.getStartedAt())
                                        .finishedAt(attendance.getFinishedAt())
                                        .build())
                .toList();
    }
}
