package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.*;
import com.vitalog.spring_diet.mapper.DashboardMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final DashboardMapper dashboardMapper;

    // --- 데모용 최근 리뷰/식단 리스트 (실 DB 연동 전까지 사용) ---
    private final List<RecentItemDTO> recentReviews = new ArrayList<>();
    private final List<RecentItemDTO> recentDiets   = new ArrayList<>();

    @PostConstruct
    void initMock() {
        // 최근 리뷰
        recentReviews.clear();
        recentReviews.add(RecentItemDTO.builder().id(301L).title("샐러드바 솔직 후기").author("yoon").date("2025-08-19").build());
        recentReviews.add(RecentItemDTO.builder().id(302L).title("단백질 파우더 비교").author("mint").date("2025-08-20").build());
        recentReviews.sort(Comparator.comparing(RecentItemDTO::getDate).reversed());

        // 최근 식단
        recentDiets.clear();
        recentDiets.add(RecentItemDTO.builder().id(201L).title("오늘의 다이어트 식단").author("diet_lee").date("2025-08-22").build());
        recentDiets.add(RecentItemDTO.builder().id(202L).title("간단 레시피 공유").author("wellbeing").date("2025-08-21").build());
        recentDiets.sort(Comparator.comparing(RecentItemDTO::getDate).reversed());
    }

    // 대시보드 요약 (회원수, BMI, 목표달성, 성별 분포, 역할 분포)
    public AdminDashboardDTO getSummaryOnly() {
        int totalMembers = dashboardMapper.countMembers();
        Double avgBmi = dashboardMapper.avgBmi();
        if (avgBmi == null) avgBmi = 0.0;

        int goalAchieved = dashboardMapper.countGoalAchieved();

        GenderRatioDTO ratio = dashboardMapper.selectGenderRatio();
        if (ratio == null) ratio = GenderRatioDTO.builder().M(0).F(0).ETC(0).build();

        List<RoleCountDTO> roles = dashboardMapper.selectRoleDistribution();

        SummaryDTO summary = SummaryDTO.builder()
                .totalMembers(totalMembers)
                .newThisWeek(0)       // 가입일 없으니 0 고정
                .avgBMI(avgBmi)
                .goalAchieved(goalAchieved)
                .roles(roles)
                .build();

        return AdminDashboardDTO.builder()
                .summary(summary)
                .genderRatio(ratio)
                .build();
    }

    // 최근 리뷰
    public List<RecentItemDTO> getRecentReviews(int limit) {
        return recentReviews.stream().limit(limit).toList();
    }

    // 최근 식단
    public List<RecentItemDTO> getRecentDiets(int limit) {
        return recentDiets.stream().limit(limit).toList();
    }
}