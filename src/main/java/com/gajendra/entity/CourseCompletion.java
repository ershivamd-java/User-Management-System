package com.gajendra.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "course_completions", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_course_completion_admission",
                columnNames = "admission_id"
        ),
        @UniqueConstraint(
                name = "uk_course_completion_certificate",
                columnNames = "certificate_number"
        )
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CourseCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "course",
            "batch"
    })
    private Student student;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admission_id", nullable = false)
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "enquiry",
            "student",
            "course",
            "batch"
    })
    private Admission admission;

    @Column(nullable = false)
    private LocalDate completionDate;

    @Column(name = "certificate_number", nullable = false, unique = true, length = 30)
    private String certificateNumber;

    @Column(length = 500)
    private String remarks;

    public CourseCompletion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}