// src/main/java/com/vitalog/spring_diet/dto/admindashboard/SummaryDTO.java
package com.vitalog.spring_diet.dto.admindashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDTO {
    private int totalMembers;

    // 아래 3개는 현재 프론트 미사용이면 0/기본값 유지
    private int newThisWeek;
    private double avgBMI;
    private int goalAchieved;

    // ★ 역할 분포(바 차트용)
    private List<RoleCountDTO> roles;
}
