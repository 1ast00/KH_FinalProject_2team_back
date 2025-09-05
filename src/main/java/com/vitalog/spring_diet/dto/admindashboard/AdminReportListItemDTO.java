package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReportListItemDTO {
    private Long reportId;
    private String targetType;     // MEAL_POST, MEAL_COMMENT, REVIEW_POST, REVIEW_COMMENT
    private Long targetId;
    private String titleSnapshot;
    private Long reporterMno;
    private String createdAt;      // YYYY-MM-DD HH24:MI
    private String status;         // PENDING, DONE
    private Long resolvedBy;       // nullable
    private String resolvedAt;     // nullable YYYY-MM-DD HH24:MI
}
