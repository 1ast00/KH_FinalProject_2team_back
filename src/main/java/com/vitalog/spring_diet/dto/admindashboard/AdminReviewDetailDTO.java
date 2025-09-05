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

    private List<CommentItem> comments;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class CommentItem {
        private Long   id;       // cno
        private String content;  // brccontent
        private String writer;   // 닉네임/아이디
        private String date;     // YYYY-MM-DD
    }
}
