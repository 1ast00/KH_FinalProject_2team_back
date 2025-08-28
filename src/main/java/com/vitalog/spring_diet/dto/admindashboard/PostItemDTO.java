package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PostItemDTO {
    private String board;        // DIET | REVIEW
    private Long id;             // 게시글 PK
    private String title;
    private String createdDate;  // yyyy-MM-dd
    private boolean visible;     // true=게시 / false=숨김
}