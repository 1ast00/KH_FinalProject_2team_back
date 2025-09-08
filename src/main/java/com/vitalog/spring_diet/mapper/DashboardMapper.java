package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.GenderRatioDTO;
import com.vitalog.spring_diet.dto.admindashboard.RecentItemDTO;
import com.vitalog.spring_diet.dto.admindashboard.RoleCountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {
    int countMembers();
    Double avgBmi();
    int countGoalAchieved();
    GenderRatioDTO selectGenderRatio();

    // 선택: 역할 분포
    List<RoleCountDTO> selectRoleDistribution();

    // 대시보드: 최근 식단 게시판 (있는 만큼 반환)
    List<RecentItemDTO> selectRecentMeals();
}
