package com.vitalog.spring_diet.config;

import com.vitalog.spring_diet.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 필요 시 CorsConfigurationSource 빈으로 상세 설정
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(f -> f.disable())
                .httpBasic(b -> b.disable())
                .authorizeHttpRequests(auth -> auth
                        // 프리플라이트는 무조건 통과
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 인증 공개 엔드포인트
                        .requestMatchers("/api/auth/**").permitAll()
                        // 총 회원 수
                        .requestMatchers("/api/admin/members/count").permitAll()
                        // 관리자 보호: 필터가 심어둔 request attribute로 판단
                        .requestMatchers("/api/admin/**").access(adminByRequestAttr())
                        // 그 외는 필요 시 열어둠(대시보드/프론트 개발 편의)
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // JwtAuthenticationFilter 가 넣어둔 request attribute("authenticatedRoles")로 ADMIN 판정
    private AuthorizationManager<RequestAuthorizationContext> adminByRequestAttr() {
        return (authentication, context) -> {
            HttpServletRequest req = context.getRequest();
            String roles = (String) req.getAttribute("authenticatedRoles");
            boolean isAdmin = roles != null && roles.toUpperCase().contains("ADMIN");
            return new AuthorizationDecision(isAdmin);
        };
    }
}
