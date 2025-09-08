package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberListItemDTO {
    private String memberNo;
    private String userid;
    private String gender;
    private String role;
    private String status;
}
