package com.vitalog.spring_diet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "반드시 아이디는 입력하셔야 합니다.")
    private String userid;
    @NotBlank(message = "반드시 비밀번호는 입력하셔야 합니다.")
    private String password;
    @NotBlank(message = "반드시 이름은 입력하셔야 합니다.")
    private String mname;
    @NotBlank(message = "반드시 별명은 입력하셔야 합니다.")
    private String nickname;
    @NotNull(message = "반드시 키를 입력하셔야 합니다.")
    private int height;
    @NotNull(message = "반드시 체중을 입력하셔야 합니다.")
    private double weight;
    @NotBlank(message = "반드시 성별을 선택하셔야 합니다.")
    private String gender;

    private int goalweight;

    //private String role; //선택 사항

}
