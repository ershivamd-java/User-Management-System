
package com.gajendra.controller;

import com.gajendra.dto.CourseCompletionRequest;
import com.gajendra.entity.CourseCompletion;
import com.gajendra.service.CertificatePdfService;
import com.gajendra.service.CourseCompletionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseCompletionController {

    private final CourseCompletionService courseCompletionService;
    private final CertificatePdfService certificatePdfService;

    public CourseCompletionController(
            CourseCompletionService courseCompletionService,
            CertificatePdfService certificatePdfService) {
        this.courseCompletionService = courseCompletionService;
        this.certificatePdfService = certificatePdfService;
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

    @GetMapping(
            value = "/students/{studentId}/certificate/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadCertificatePdf(
            @PathVariable Long studentId) {

        CourseCompletion completion =
                courseCompletionService.getCertificate(studentId);

        byte[] pdf = certificatePdfService.generateCertificatePdf(completion);

        String filename = "certificate-"
                + completion.getCertificateNumber() + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(pdf);
    }

    @GetMapping("/course-completions")
    public ResponseEntity<List<CourseCompletion>> getAllCompletions() {

        List<CourseCompletion> completions =
                courseCompletionService.getAllCompletions();

        return ResponseEntity.ok(completions);
    }
}
