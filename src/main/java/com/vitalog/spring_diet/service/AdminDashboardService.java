package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.AdminDashboardDTO;
import com.vitalog.spring_diet.dto.admindashboard.GenderRatioDTO;
import com.vitalog.spring_diet.dto.admindashboard.RecentItemDTO;
import com.vitalog.spring_diet.dto.admindashboard.SummaryDTO;
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

    // ---- 데모/폴백용 최근 리스트 (실DB 연동 전까지 사용) ----
    private final List<RecentItemDTO> recentReviews = new ArrayList<>();
    private final List<RecentItemDTO> recentDiets = new ArrayList<>();

    @PostConstruct
    void initMock() {
        // 최신순 상단 노출 가정
        recentReviews.clear();
        recentReviews.add(RecentItemDTO.builder().id(301L).title("다이어트 도시락 리뷰").author("mint").date("2025-08-20").build());
        recentReviews.add(RecentItemDTO.builder().id(302L).title("샐러드바 솔직 후기").author("yoon").date("2025-08-19").build());
        recentReviews.sort(Comparator.comparing(RecentItemDTO::getDate).reversed());

        recentDiets.clear();
        recentDiets.add(RecentItemDTO.builder().id(201L).title("오늘의 다이어트 식단").author("diet_lee").date("2025-08-22").build());
        recentDiets.add(RecentItemDTO.builder().id(202L).title("웰빙 레시피 공유").author("wellbeing").date("2025-08-21").build());
        recentDiets.sort(Comparator.comparing(RecentItemDTO::getDate).reversed());
    }

    // ---- 요약(총 이용자, 성별) ----
    public AdminDashboardDTO getSummaryOnly() {
        int totalMembers = dashboardMapper.countMembers();
        Double avgBmi = dashboardMapper.avgBmi();
        if (avgBmi == null) avgBmi = 0.0;

        int goalAchieved = dashboardMapper.countGoalAchieved();

        GenderRatioDTO ratio = dashboardMapper.selectGenderRatio();
        if (ratio == null) {
            ratio = GenderRatioDTO.builder().M(0).F(0).ETC(0).build();
        }

        SummaryDTO summary = SummaryDTO.builder()
                .totalMembers(totalMembers)
                .newThisWeek(0) // 가입일 컬럼 없음 → 0 고정
                .avgBMI(avgBmi)
                .goalAchieved(goalAchieved)
                .build();

        return AdminDashboardDTO.builder()
                .summary(summary)
                .genderRatio(ratio)
                .build();
    }

    // ---- 최근 리스트 (필요시 Mapper 쿼리로 교체 예정) ----
    public List<RecentItemDTO> getRecentReviews(int limit) {
        return recentReviews.stream().limit(limit).toList();
    }

    public List<RecentItemDTO> getRecentDiets(int limit) {
        return recentDiets.stream().limit(limit).toList();
    }
}