package io.devlabs.keytree.domains.attendance.application.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

import io.devlabs.keytree.domains.attendance.domain.AttendanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStartAttendanceRequest {
    private Long userId;

    private AttendanceType attendanceType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedAt;
}
