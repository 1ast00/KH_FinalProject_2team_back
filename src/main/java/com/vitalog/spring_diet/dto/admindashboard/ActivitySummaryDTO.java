package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ActivitySummaryDTO {
    private int dietPostCount;
    private int reviewPostCount;
    private int dietCommentCount;
    private int reviewCommentCount;
}