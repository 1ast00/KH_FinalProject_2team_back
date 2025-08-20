package com.vitallog.spring_diet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "반드시 아이디는 입력하셔야 합니다.")
    private String AuthenticationId;
    @NotBlank(message = "반드시 비밀번호는 입력하셔야 합니다.")
    private String AuthenticationPassword;

}
