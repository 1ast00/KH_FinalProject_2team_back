package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.config.PasswordEncoder;
import com.vitalog.spring_diet.dto.AuthRequest;
import com.vitalog.spring_diet.dto.JwtResponse;
import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.service.MemberService;
import com.vitalog.spring_diet.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

        //msg 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "사용자가 정상적으로 등록되었습니다."
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login (@Valid @RequestBody AuthRequest request, HttpServletResponse response){
        //아이디값 가져오기
        MemberDTO user = memberService.findByMemberid(request.getUserid());

        //유효성 검사(아이디 및 암호)
        if(user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

        refreshTokenCookie.setHttpOnly(true); //자바스크립트에서 입력이 불가능하게

//      refreshTokenCookie.setSecure(true); 배포하면 이거 활성화 https
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int)(jwtTokenProvider
                .getRefreshTokenExpirationMs() / 1000));

        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, "Bearer"));

    }

    //사용자 정보 가져오기
    @GetMapping("/user-data")
    public ResponseEntity<String> getUserData(HttpServletRequest request){
        String mno = (String) request.getAttribute("authenticatedUsermno");

        MemberDTO user = memberService.findByid(Integer.parseInt(mno));

        System.out.println("일련번호 확인: " + mno);
        System.out.println("사용자: " + user);

        if(user == null) {
            Map<String, String> errorResponse = Map.of("message" , "사용자를 찾을 수 없습니다.","mno", mno);
            return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        //null처리
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);

        refreshTokenCookie.setHttpOnly(true);
//      refreshTokenCookie.setSecure(true); 배포시
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);

        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok("로그아웃");
    }

    @PostMapping("/findID")
    public ResponseEntity<Object> findID(@Valid @RequestBody MemberDTO member){
        String mname = member.getMname();
        String nickname = member.getNickname();

        MemberDTO findID = memberService.findID(mname, nickname);

        if(findID != null){
            Map<String, String> map = Map.of("userid", findID.getUserid());
            return ResponseEntity.ok(map);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/findPW")
    public ResponseEntity<Object> findPW(@Valid @RequestBody MemberDTO member){
        String userid = member.getUserid();
        String mname = member.getMname();

        MemberDTO findPW = memberService.findPW(userid, mname);

        if(findPW != null) {
            Map<String, String> map = new HashMap<>();
            map.put("userid", findPW.getUserid());
            map.put("mname", findPW.getMname());

            return ResponseEntity.ok(map);

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/resetPW")
    public ResponseEntity<Object> resetPW(@Valid @RequestBody AuthRequest request){
        String userid = request.getUserid();
        String password = passwordEncoder.encode(request.getPassword());

        memberService.updatePW(userid, password);

        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody MemberDTO member){
        String mname = member.getMname();
        String nickname = member.getNickname();
        int goalweight = member.getGoalweight();
        String userid = member.getUserid();

        memberService.updateUser(mname, nickname, goalweight, userid);

        return ResponseEntity.ok().body("사용자 정보가 성공적으로 변경되었습니다.");
    }
}
