package com.gajendra.service;

import com.gajendra.dto.CourseCompletionRequest;
import com.gajendra.entity.Admission;
import com.gajendra.entity.AdmissionStatus;
import com.gajendra.entity.CourseCompletion;
import com.gajendra.entity.Student;
import com.gajendra.exception.ResourceNotFoundException;
import com.gajendra.repository.AdmissionRepository;
import com.gajendra.repository.CourseCompletionRepository;
import com.gajendra.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@Transactional
public class CourseCompletionService {

    private final StudentRepository studentRepository;
    private final AdmissionRepository admissionRepository;
    private final CourseCompletionRepository courseCompletionRepository;

    public CourseCompletionService(StudentRepository studentRepository,
                                   AdmissionRepository admissionRepository,
                                   CourseCompletionRepository courseCompletionRepository) {
        this.studentRepository = studentRepository;
        this.admissionRepository = admissionRepository;
        this.courseCompletionRepository = courseCompletionRepository;
    }

    public CourseCompletion completeCourse(Long studentId,
                                           CourseCompletionRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found: " + studentId));

        Admission admission = admissionRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admission not found for student: " + studentId));

        if (courseCompletionRepository.existsByAdmissionId(admission.getId())) {
            throw new IllegalStateException(
                    "This admission is already completed.");
        }

        if (admission.getStatus() == AdmissionStatus.COMPLETED) {
            throw new IllegalStateException(
                    "This admission is already completed.");
        }

        CourseCompletion completion = new CourseCompletion();

        completion.setStudent(student);
        completion.setAdmission(admission);
        completion.setCompletionDate(LocalDate.now());
        completion.setCertificateNumber(generateCertificateNumber());

        if (request != null) {
            completion.setRemarks(request.getRemarks());
        }

        admission.setStatus(AdmissionStatus.COMPLETED);

        courseCompletionRepository.save(completion);
        admissionRepository.save(admission);

        return completion;
    }

    @Transactional(readOnly = true)
    public CourseCompletion getCertificate(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                    "Student not found: " + studentId);
        }

        return courseCompletionRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Certificate not found for student: " + studentId));
    }

    @Transactional(readOnly = true)
    public List<CourseCompletion> getAllCompletions() {
        return courseCompletionRepository.findAll();
    }

    private String generateCertificateNumber() {

        String year = String.valueOf(Year.now().getValue());
        String prefix = "TF-CERT-" + year + "-";

        return courseCompletionRepository
                .findTopByCertificateNumberStartingWithOrderByIdDesc(prefix)
                .map(last -> {

                    String numberPart =
                            last.getCertificateNumber()
                                    .substring(prefix.length());

                    int next = Integer.parseInt(numberPart) + 1;

                    return prefix + String.format("%04d", next);
                })
                .orElse(prefix + "0001");
    }
}