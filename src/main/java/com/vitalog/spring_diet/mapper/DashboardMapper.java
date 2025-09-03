// src/main/java/com/vitalog/spring_diet/mapper/DashboardMapper.java
package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.GenderRatioDTO;
import com.vitalog.spring_diet.dto.admindashboard.RoleCountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DashboardMapper {
    int countMembers();
    Double avgBmi();
    int countGoalAchieved();
    GenderRatioDTO selectGenderRatio();

    // 선택: 역할 분포가 필요할 때 사용
    List<RoleCountDTO> selectRoleDistribution();
}
