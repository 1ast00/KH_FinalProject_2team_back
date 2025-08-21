package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.config.PasswordEncoder;
import com.vitalog.spring_diet.dto.AuthRequest;
import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.service.MemberService;
import com.vitalog.spring_diet.util.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
//        if(memberService.findByMemberid(request.getUserid())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자 아이디가 이미 존재합니다.");
//        } 작업중입니다.

        //새로운 회원값 전송
        MemberDTO newMember = new MemberDTO();
        //자동으로 증가할려면 SEQ -> 우리가 부를때만 올라가는거지
        //만들때 올라가는게 -> TRG
        //TRG 만들어야 하는데.
        //nextval -> seq_membe
        newMember.setUserid(request.getUserid());
        newMember.setPassword(passwordEncoder.encode(request.getPassword()));
        //작업중

        return null;
    }
}
