package io.devlabs.keytree.domains.attendance.presentation;

import io.devlabs.keytree.domains.attendance.application.application.AttendanceService;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/attendances")
    public CreateStartAttendanceResponse createStartAttendance(@RequestBody CreateStartAttendanceRequest request) {
        return attendanceService.createStartAttendance(request);
    }
}
