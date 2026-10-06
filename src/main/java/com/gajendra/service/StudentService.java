package com.gajendra.service;

import com.gajendra.entity.Batch;
import com.gajendra.dto.StudentProfileResponse;
import com.gajendra.dto.StudentUpdateRequest;
import com.gajendra.entity.Admission;
import com.gajendra.repository.AdmissionRepository;

import java.util.Optional;
import com.gajendra.entity.Student;
import com.gajendra.exception.ResourceNotFoundException;
import com.gajendra.repository.BatchRepository;
import com.gajendra.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;
    private final AdmissionRepository admissionRepository;
    
    public StudentService(StudentRepository studentRepository,
                          BatchRepository batchRepository, AdmissionRepository admissionRepository ) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
        this.admissionRepository= admissionRepository;
    }

    // Task 3: Get all students
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Task 3: Get student by ID
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + id));
    }

    // Task 4: Get all students of a particular batch
    @Transactional(readOnly = true)
    public List<Student> getStudentsByBatch(Long batchId) {

        // Check whether batch exists
        batchRepository.findById(batchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found: " + batchId));

        return studentRepository.findByBatchId(batchId);
    }

    // Task 4: Get student's current batch
    @Transactional(readOnly = true)
    public Batch getStudentBatch(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + studentId));

        if (student.getBatch() == null) {
            throw new IllegalStateException(
                    "Student is not assigned to any batch: " + studentId);
        }

        return student.getBatch();
    }

    // Task 4: Transfer student to another batch
    public Student transferStudentToBatch(
            Long studentId,
            Long newBatchId) {

        // Find student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + studentId));

        // Find new batch
        Batch newBatch = batchRepository.findById(newBatchId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Batch not found: " + newBatchId));

        // New batch active hai ya nahi
        if (Boolean.FALSE.equals(newBatch.getActive())) {
            throw new IllegalArgumentException(
                    "Cannot transfer student to an inactive batch");
        }

        // Student already same batch me hai
        if (student.getBatch() != null
                && student.getBatch().getId().equals(newBatchId)) {

            throw new IllegalArgumentException(
                    "Student is already assigned to this batch");
        }

        // Batch capacity check
        long currentStudents =
                studentRepository.countByBatchId(newBatchId);

        Integer maxStudents = newBatch.getMaxStudents();

        if (maxStudents != null
                && currentStudents >= maxStudents) {

            throw new IllegalArgumentException(
                    "Batch capacity is full. Maximum students allowed: "
                            + maxStudents);
        }

        // Course compatibility check
        if (student.getCourse() != null
                && newBatch.getCourse() != null
                && !student.getCourse().getId()
                        .equals(newBatch.getCourse().getId())) {

            throw new IllegalArgumentException(
                    "Student cannot be transferred to a batch "
                            + "of another course");
        }

        // Transfer student
        student.setBatch(newBatch);

        return studentRepository.save(student);
        
    }
 // Task 7: Search students by name
    @Transactional(readOnly = true)
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByStudentNameContainingIgnoreCase(name);
    }

    // Task 7: Search students by mobile
    @Transactional(readOnly = true)
    public List<Student> searchStudentsByMobile(String mobile) {
        return studentRepository.findByMobile(mobile);
    }

    // Task 7: Find student by admission number
    @Transactional(readOnly = true)
    public Student getStudentByAdmissionNumber(String admissionNumber) {

        Admission admission = admissionRepository
                .findByAdmissionNumber(admissionNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admission not found: " + admissionNumber));

        return admission.getStudent();
    }

    // Task 7: Get complete student profile
    @Transactional(readOnly = true)
    public StudentProfileResponse getStudentProfile(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + studentId));

        Admission admission = admissionRepository.findByStudentId(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admission not found for student: " + studentId));

        String courseName = admission.getCourse() != null
                ? admission.getCourse().getCourseName()
                : null;

        String batchName = admission.getBatch() != null
                ? admission.getBatch().getBatchName()
                : null;

        return new StudentProfileResponse(
                student.getStudentName(),
                student.getMobile(),
                student.getEmail(),
                student.getAddress(),
                admission.getAdmissionNumber(),
                courseName,
                batchName,
                admission.getAdmissionDate(),
                "ADMITTED"
        );
    }

    // Task 7: Update student basic details
    public Student updateStudent(
            Long studentId,
            StudentUpdateRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found: " + studentId));

        student.setStudentName(request.getStudentName());
        student.setMobile(request.getMobile());
        student.setEmail(request.getEmail());
        student.setAddress(request.getAddress());

        return studentRepository.save(student);
    }
}
