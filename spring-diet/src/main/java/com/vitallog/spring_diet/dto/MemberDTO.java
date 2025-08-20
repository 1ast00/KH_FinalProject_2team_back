package com.vitallog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("member")
public class MemberDTO {
    private int mno;
    private String userid;
    private String password;
    private String mname;
    private String nickname;
    private int height;
    private double weight;
    private String gender;
    private double goalweight;
    //ROLE_USER, ROLE_ADMIN
    private String role;
}
