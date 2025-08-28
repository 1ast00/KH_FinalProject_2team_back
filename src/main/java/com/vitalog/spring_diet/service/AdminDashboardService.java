// src/main/java/com/vitalog/spring_diet/service/AdminDashboardService.java
package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.AdminDashboardDTO;
import com.vitalog.spring_diet.dto.admindashboard.GenderRatioDTO;
import com.vitalog.spring_diet.dto.admindashboard.SummaryDTO;
import com.vitalog.spring_diet.mapper.DashboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final DashboardMapper dashboardMapper;

    // 프론트 계약: AdminDashboardDTO(summary + genderRatio)
    public AdminDashboardDTO getSummaryOnly() {
        int totalMembers = dashboardMapper.countMembers();

        Double avgBmi = dashboardMapper.avgBmi();
        if (avgBmi == null) avgBmi = 0.0;

        int goalAchieved = dashboardMapper.countGoalAchieved();

        GenderRatioDTO ratio = dashboardMapper.selectGenderRatio();
        if (ratio == null) {
            ratio = GenderRatioDTO.builder().M(0).F(0).ETC(0).build();
        }

        // ERD에 가입일 컬럼이 없으므로 newThisWeek = 0 고정
        SummaryDTO summary = SummaryDTO.builder()
                .totalMembers(totalMembers)
                .newThisWeek(0)
                .avgBMI(avgBmi)
                .goalAchieved(goalAchieved)
                .build();

        return AdminDashboardDTO.builder()
                .summary(summary)
                .genderRatio(ratio)
                .build();
    }

    // 선택: 단독 조회용
    public GenderRatioDTO getGenderRatio() {
        GenderRatioDTO ratio = dashboardMapper.selectGenderRatio();
        if (ratio == null) ratio = GenderRatioDTO.builder().M(0).F(0).ETC(0).build();
        return ratio;
    }
}
