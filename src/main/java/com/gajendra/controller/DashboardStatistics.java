package com.gajendra.controller;

public class DashboardStatistics {

    private long totalUsers;
    private long totalEnquiries;
    private long totalAdmissions;
    private long totalBatches;
    private long totalAttendance;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalEnquiries() {
        return totalEnquiries;
    }

    public void setTotalEnquiries(long totalEnquiries) {
        this.totalEnquiries = totalEnquiries;
    }

    public long getTotalAdmissions() {
        return totalAdmissions;
    }

    public void setTotalAdmissions(long totalAdmissions) {
        this.totalAdmissions = totalAdmissions;
    }

    public long getTotalBatches() {
        return totalBatches;
    }

    public void setTotalBatches(long totalBatches) {
        this.totalBatches = totalBatches;
    }

    public long getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(long totalAttendance) {
        this.totalAttendance = totalAttendance;
    }
}