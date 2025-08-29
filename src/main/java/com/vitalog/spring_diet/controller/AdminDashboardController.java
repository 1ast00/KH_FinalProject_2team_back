package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminDashboardDTO;
import com.vitalog.spring_diet.dto.admindashboard.RecentItemDTO;
import com.vitalog.spring_diet.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/summary")
    public AdminDashboardDTO getSummary() {
        return adminDashboardService.getSummaryOnly();
    }

    // 최근 리뷰 게시판
    @GetMapping("/recent-reviews")
    public List<RecentItemDTO> recentReviews(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        return adminDashboardService.getRecentReviews(limit);
    }

    // 최근 식단 게시판
    @GetMapping("/recent-diets")
    public List<RecentItemDTO> recentDiets(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        return adminDashboardService.getRecentDiets(limit);
    }
}