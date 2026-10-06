package com.gajendra.repository;

import com.gajendra.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    boolean existsByEnquiryId(Long enquiryId);

    Optional<Admission> findTopByAdmissionNumberStartingWithOrderByIdDesc(String prefix);
    Optional<Admission> findByAdmissionNumber(String admissionNumber);

    Optional<Admission> findByStudentId(Long studentId);
}
