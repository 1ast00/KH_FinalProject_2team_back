package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberListItemDTO {
    private String memberNo;  // LPAD 4자리 "0001"
    private String userid;
    private String role;      // USER / ADMIN
    private String status;    // ACTIVE / SUSPENDED (컬럼 없으면 'ACTIVE')
}