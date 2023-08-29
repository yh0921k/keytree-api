package io.devlabs.keytree.domains.attendance.application;

import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import io.devlabs.keytree.domains.attendance.domain.Attendance;
import io.devlabs.keytree.domains.attendance.domain.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
