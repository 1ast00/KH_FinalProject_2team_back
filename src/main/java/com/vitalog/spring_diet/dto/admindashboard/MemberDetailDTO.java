package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;
import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberDetailDTO {
    private MemberBasicDTO basic;
    private ActivitySummaryDTO summary;
    private List<PostItemDTO> recentPosts;
    private List<CommentItemDTO> recentComments;
}