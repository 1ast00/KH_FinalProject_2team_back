package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReviewDetailDTO {
    private Long   brno;
    private String title;
    private String content;
    private String writer;     // NVL(nickname, userid)
    private String writeDate;  // YYYY-MM-DD
    private String status;     // 게시/숨김
    private Integer danger;    // brdanger


    private Integer reportPostCount;     // 이 리뷰 글 신고 수
    private Integer reportCommentCount;  // 이 리뷰의 댓글 신고 수

    private List<CommentItem> comments;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class CommentItem {
        private Long   id;       // cno (프로젝트 스키마에 맞춰 brcno면 그대로 사용)
        private String content;  // brccontent
        private String writer;   // 닉네임/아이디
        private String date;     // YYYY-MM-DD
    }
}
