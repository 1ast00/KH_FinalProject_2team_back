package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminMealsListItemDTO {
    private Long bmno;        // 게시글 PK
    private Long mno;         // 작성자 PK
    private String title;
    private String writer;    // 닉네임(없으면 userid)
    private String writeDate;
    private String visible;
}
