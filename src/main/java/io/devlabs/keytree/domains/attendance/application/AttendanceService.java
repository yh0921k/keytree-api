package io.devlabs.keytree.domains.attendance.application;

import io.devlabs.keytree.domains.attendance.application.dto.CreateFinishAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateFinishAttendanceResponse;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import io.devlabs.keytree.domains.attendance.domain.Attendance;
import io.devlabs.keytree.domains.attendance.domain.AttendanceRepository;
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

    @Transactional
    public CreateFinishAttendanceResponse createFinishAttendance(Long attendanceId, CreateFinishAttendanceRequest request) {
        Attendance attendance =
                attendanceRepository
                        .findById(attendanceId)
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 퇴근 요청입니다."));

        attendance.finish(
                request.getFinishedAt()
        );

        return CreateFinishAttendanceResponse.builder()
                .id(attendance.getId())
                .userId(attendance.getUserId())
                .startedAt(attendance.getCreatedAt())
                .finishedAt(attendance.getFinishedAt())
                .build();
    }


    @Transactional(readOnly = true)
    public List<CreateFinishAttendanceResponse> getAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances.stream()
                .map(
                        attendance ->
                                CreateFinishAttendanceResponse.builder()
                                        .id(attendance.getId())
                                        .userId(attendance.getUserId())
                                        .startedAt(attendance.getStartedAt())
                                        .finishedAt(attendance.getFinishedAt())
                                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public CreateFinishAttendanceResponse getAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository
                .findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 출석입니다."));

        return CreateFinishAttendanceResponse.builder()
                .id(attendance.getId())
                .userId(attendance.getUserId())
                .startedAt(attendance.getStartedAt())
                .finishedAt(attendance.getFinishedAt())
                .build();
    }
}
