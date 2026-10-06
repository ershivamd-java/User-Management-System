package com.gajendra.dto;

import java.time.LocalDate;

public class StudentProfileResponse {

    private String studentName;
    private String mobile;
    private String email;
    private String address;
    private String admissionNumber;
    private String course;
    private String batch;
    private LocalDate joiningDate;
    private String admissionStatus;

    public StudentProfileResponse() {
    }

    public StudentProfileResponse(
            String studentName,
            String mobile,
            String email,
            String address,
            String admissionNumber,
            String course,
            String batch,
            LocalDate joiningDate,
            String admissionStatus) {

        this.studentName = studentName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.admissionNumber = admissionNumber;
        this.course = course;
        this.batch = batch;
        this.joiningDate = joiningDate;
        this.admissionStatus = admissionStatus;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getAdmissionStatus() {
        return admissionStatus;
    }

    public void setAdmissionStatus(String admissionStatus) {
        this.admissionStatus = admissionStatus;
    }
}