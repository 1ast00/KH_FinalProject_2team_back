package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthorActivityDTO {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class AuthorInfo {
        private Long mno;
        private String userid;
        private String nickname;
        private int postCount;
        private int totalPostReports;     // BoardMeals.bmdanger 합
        private int totalCommentReports;  // BMComment.bmdanger 합
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class PostBrief {
        private Long bmno;
        private String title;
        private String writeDate;
        private int likeCount;     // BMAwesome 수
        private int commentCount;  // BMComment 수
        private int reportCount;   // bmdanger
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class SelfCommentBrief {
        private Long bmcommentno;
        private Long bmno;
        private String postTitle;
        private String content;
        private String writeDate;
        private int likeCount;    // BMCommentAwesome 수
        private int reportCount;  // bmdanger
    }

    private AuthorInfo author;
    private List<PostBrief> posts;
    private List<SelfCommentBrief> selfCommentsOnOwnPosts;
}
