package com.vitalog.spring_diet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "반드시 아이디는 입력하셔야 합니다.")
    private String userid;
    @NotBlank(message = "반드시 비밀번호는 입력하셔야 합니다.")
    private String password;

    //선택 사항
    private String mname;
    private String nickname;
    private int height;
    private double weight;
    private String gender;
    private int goalweight;
    //private String role;

}
