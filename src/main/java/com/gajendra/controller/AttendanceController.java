package com.gajendra.controller;

import com.gajendra.dto.AttendanceRequest;
import com.gajendra.entity.Attendance;
import com.gajendra.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // 1. Mark Attendance
    @PostMapping
    public ResponseEntity<Attendance> markAttendance(
            @RequestBody AttendanceRequest request) {

        return ResponseEntity.ok(
                attendanceService.markAttendance(request)
        );
    }

    // 2. Get batch date-wise attendance
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Attendance>> getBatchAttendance(
            @PathVariable Long batchId,
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(
                attendanceService.getBatchAttendance(
                        batchId,
                        date
                )
        );
    }

    // 3. Get student complete attendance history
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getStudentAttendance(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                attendanceService.getStudentAttendance(
                        studentId
                )
        );
    }
}
