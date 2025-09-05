package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReviewsListItemDTO {
    private Long brno;
    private Long mno;
    private String title;
    private String writer;     // NVL(nickname, userid)
    private String writeDate;  // YYYY-MM-DD
    private String visible;    // 게시/숨김
}
