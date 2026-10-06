package com.gajendra.controller;

import com.gajendra.dto.CourseCompletionRequest;
import com.gajendra.entity.CourseCompletion;
import com.gajendra.service.CourseCompletionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseCompletionController {

    private final CourseCompletionService courseCompletionService;

    public CourseCompletionController(
            CourseCompletionService courseCompletionService) {
        this.courseCompletionService = courseCompletionService;
    }

    @PostMapping("/students/{studentId}/complete-course")
    public ResponseEntity<CourseCompletion> completeCourse(
            @PathVariable Long studentId,
            @RequestBody(required = false) CourseCompletionRequest request) {

        CourseCompletion completion =
                courseCompletionService.completeCourse(studentId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(completion);
    }

    @GetMapping("/students/{studentId}/certificate")
    public ResponseEntity<CourseCompletion> getCertificate(
            @PathVariable Long studentId) {

        CourseCompletion completion =
                courseCompletionService.getCertificate(studentId);

        return ResponseEntity.ok(completion);
    }

    @GetMapping("/course-completions")
    public ResponseEntity<List<CourseCompletion>> getAllCompletions() {

        List<CourseCompletion> completions =
                courseCompletionService.getAllCompletions();

        return ResponseEntity.ok(completions);
    }
}