// src/main/java/com/vitalog/spring_diet/controller/AdminDashboardController.java
package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminDashboardDTO;
import com.vitalog.spring_diet.dto.admindashboard.GenderRatioDTO;
import com.vitalog.spring_diet.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    // 프론트는 이 한 API만 호출하면 됨: summary + genderRatio 동시 반환
    @GetMapping("/summary")
    public ResponseEntity<AdminDashboardDTO> getSummary() {
        return ResponseEntity.ok(adminDashboardService.getSummaryOnly());
    }

    // (선택) 호환용: 성별만 단독 조회가 필요할 때 사용
    @GetMapping("/gender-ratio")
    public ResponseEntity<GenderRatioDTO> getGenderRatio() {
        return ResponseEntity.ok(adminDashboardService.getGenderRatio());
    }
}
