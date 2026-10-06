package com.gajendra.repository;

import com.gajendra.entity.CourseCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseCompletionRepository
        extends JpaRepository<CourseCompletion, Long> {

    boolean existsByAdmissionId(Long admissionId);

    Optional<CourseCompletion> findByStudentId(Long studentId);

    Optional<CourseCompletion> findTopByCertificateNumberStartingWithOrderByIdDesc(
            String prefix
    );
}