package io.devlabs.keytree.domains.attendance.presentation;

import io.devlabs.keytree.domains.attendance.application.application.AttendanceService;
import io.devlabs.keytree.domains.attendance.application.dto.CreateFinishAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateFinishAttendanceResponse;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceRequest;
import io.devlabs.keytree.domains.attendance.application.dto.CreateStartAttendanceResponse;
import io.devlabs.keytree.domains.attendance.domain.Attendance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/attendances")
    public CreateStartAttendanceResponse createStartAttendance(@RequestBody CreateStartAttendanceRequest request) {
        return attendanceService.createStartAttendance(request);
    }

    @PatchMapping("/attendances/{attendanceId}")
    public CreateFinishAttendanceResponse createFinishAttendance(@PathVariable Long attendanceId, @RequestBody CreateFinishAttendanceRequest request) {
        return attendanceService.createFinishAttendance(attendanceId, request);
    }


    @GetMapping("/attendances")
    public List<CreateFinishAttendanceResponse> getAttendances() {
        return attendanceService.getAttendances();
    }
}
