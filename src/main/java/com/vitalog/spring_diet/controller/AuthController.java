package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.config.PasswordEncoder;
import com.vitalog.spring_diet.dto.AuthRequest;
import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.service.MemberService;
import com.vitalog.spring_diet.util.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest request){
        //아이디 중복 체크
        if(memberService.findByMemberid(request.getUserid()) != null){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자 아이디가 이미 존재합니다.");
        }

        //새로운 회원값 전송
        MemberDTO newMember = new MemberDTO();

        newMember.setUserid(request.getUserid());
        newMember.setPassword(passwordEncoder.encode(request.getPassword()));
        newMember.setMname(request.getMname());
        newMember.setNickname(request.getNickname());
        newMember.setHeight(request.getHeight());
        newMember.setWeight(request.getWeight());
        newMember.setGender(request.getGender());
        newMember.setGoalweight(request.getGoalweight());
        newMember.setRole("ROLE_USER");

        memberService.registerMember(newMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "사용자가 정상적으로 등록되었습니다."
        );
    }


}
