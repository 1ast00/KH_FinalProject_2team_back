package com.vitallog.spring_diet.controller;

import com.vitallog.spring_diet.config.PasswordEncoder;
import com.vitallog.spring_diet.service.MemberService;
import com.vitallog.spring_diet.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//경로 반드시 무슨 일이 있어도 확인할 것
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


}
