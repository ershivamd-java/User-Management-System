package com.gajendra.controller;

import com.gajendra.entity.Batch;
import com.gajendra.dto.StudentProfileResponse;
import com.gajendra.dto.StudentUpdateRequest;
import jakarta.validation.Valid;
import com.gajendra.entity.Student;
import com.gajendra.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Get student's current batch
    @GetMapping("/{studentId}/batch")
    public ResponseEntity<Batch> getStudentBatch(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                studentService.getStudentBatch(studentId)
        );
    }

    // Transfer student to another batch
    @PutMapping("/{studentId}/batch/{newBatchId}")
    public ResponseEntity<Student> transferStudent(
            @PathVariable Long studentId,
            @PathVariable Long newBatchId) {

        return ResponseEntity.ok(
                studentService.transferStudentToBatch(
                        studentId,
                        newBatchId
                )
        );
    }
 // Task 7: Search students by name or mobile
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mobile) {

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(
                    studentService.searchStudentsByName(name)
            );
        }

        if (mobile != null && !mobile.isBlank()) {
            return ResponseEntity.ok(
                    studentService.searchStudentsByMobile(mobile)
            );
        }

        return ResponseEntity.badRequest().build();
    }

    // Task 7: Get student by admission number
    @GetMapping("/admission/{admissionNumber}")
    public ResponseEntity<Student> getStudentByAdmissionNumber(
            @PathVariable String admissionNumber) {

        return ResponseEntity.ok(
                studentService.getStudentByAdmissionNumber(admissionNumber)
        );
    }

    // Task 7: Complete student profile
    @GetMapping("/{studentId}/profile")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                studentService.getStudentProfile(studentId)
        );
    }

    // Task 7: Update student basic details
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentUpdateRequest request) {

        return ResponseEntity.ok(
                studentService.updateStudent(studentId, request)
        );
    }
}
