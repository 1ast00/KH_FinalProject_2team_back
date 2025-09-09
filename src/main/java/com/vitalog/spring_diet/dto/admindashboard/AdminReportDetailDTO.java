// src/main/java/com/vitalog/spring_diet/dto/admindashboard/AdminReportDetailDTO.java
package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReportDetailDTO {
    private Long reportId;
    private String targetType;     // MEAL_POST / MEAL_COMMENT / REVIEW_POST / REVIEW_COMMENT
    private Long targetId;
    private Long reporterMno;
    private String status;

    // 스냅샷(저장 컬럼 없이 실시간 조회)
    private String targetWriter;   // 작성자 닉/아이디
    private String postTitle;      // 글인 경우
    private String commentExcerpt; // 댓글인 경우
}
