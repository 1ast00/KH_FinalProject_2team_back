package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("weeklyRanking") //프론트엔드용 임시객체
public class BoardWeeklyRankingDTO {
    private String nickname;
    private double bmi;
    private double goalWeight;
    private int ageGroup;
}