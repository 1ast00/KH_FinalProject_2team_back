package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CommentItemDTO {
    private String board;        // DIET | REVIEW
    private Long id;             // 댓글 PK
    private Long postId;         // 대상 글 PK
    private String postTitle;    // 대상 글 제목
    private String excerpt;      // 댓글 한 줄
    private String createdDate;  // yyyy-MM-dd
    private boolean visible;
}