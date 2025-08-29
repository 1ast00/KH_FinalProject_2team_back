package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberBasicDTO {
    private String memberNo;
    private Long mno;

    private String userid;
    private String nickname;
    private String gender;  // M/F/ETC

    private Double height;
    private Double weight;
    private Double goalWeight;

    private String role;
    private String status;  // ACTIVE / SUSPENDED
}