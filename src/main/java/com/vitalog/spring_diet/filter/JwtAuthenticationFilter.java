package com.vitalog.spring_diet.filter;

import com.vitalog.spring_diet.util.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            //인증이 필요없는 경로 목록을 String값으로 작성
            //예시: "/api/auth/login"
            "/api/auth/login",
            "/api/auth/register"
    );

    //토큰 추출 메소드
    private String resolveToken(HttpServletRequest servletRequest) {
        String bearerToken = servletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        //1. 사전요청(pre-flight)하는 request는 통과
        if(httpRequest.getMethod().equals("OPTIONS")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //2. 요청경로 추출
        String path = httpRequest.getServletPath();
        //System.out.println("경로 = " + path);

        //3. PUBLIC_PATHS에 포함된 경로인지 확인
        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        //4. PUBLIC_PATHS를 포함한 경로라면 인증없이 통과
        if(isPublicPath){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        
        //5. 인증(유효한 토큰)이 있어야 통과
        //http header에서 토큰 추출
        String token = resolveToken(httpRequest);
        //토큰 유효성 검사
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰이 유효할시 httprequest 객체에 저장
            String mno = jwtTokenProvider.getUsermnoFromToken(token);
            String roles = jwtTokenProvider.getRolesFromToken(token);

            httpRequest.setAttribute("authenticatedUsermno",mno);
            httpRequest.setAttribute("authenticatedRoles",roles);


            filterChain.doFilter(httpRequest,httpResponse);
        } else {
            //토큰이 유효하지 않을때(401)
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter()
                    .write("{\"message\" : \"권한 없음: 유효하지 않거나 토큰이 존재하지 않습니다.\"}");
        }
    }
}
