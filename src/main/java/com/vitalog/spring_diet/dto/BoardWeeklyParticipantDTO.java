package com.vitalog.spring_diet.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoardWeeklyParticipantDTO {
    private int mno;
    private String nickname;
    private double weight;
    private double height;
    private double goalWeight;
    private int ageGroup; // DB에 저장된 연령대 정보를 직접 가져올 필드
    private LocalDateTime participationTime; // 챌린지 참여 시간
}
