package com.gajendra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gajendra.repository.UserRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/statistics")
    public ResponseEntity<DashboardStatistics> getStatistics() {

        DashboardStatistics statistics = new DashboardStatistics();

        statistics.setTotalUsers(userRepository.count());

        return ResponseEntity.ok(statistics);
    }
}