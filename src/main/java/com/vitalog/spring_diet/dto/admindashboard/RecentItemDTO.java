package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class RecentItemDTO {
    private Long id;        // 게시글/리뷰 PK
    private String title;   // 제목
    private String author;  // 작성자 표시명(닉네임 등)
    private String date;    // yyyy-MM-dd
}