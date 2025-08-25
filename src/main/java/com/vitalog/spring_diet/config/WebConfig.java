package com.vitalog.spring_diet.config;

import com.vitalog.spring_diet.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 전역설정에서 제일 먼저 실행되는 필터, CorsFilter를 만듬 (registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE))
        
        // 필터가 실행되는 순서: FilterChain
        // registrationBean.setOrder()로 실행순서를 결정

        // /*: depth까지만 매칭
        // /**: 하위경로 전부 매칭

        registry.addMapping("/**")//모든 경로 CORS 허용
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*").allowCredentials(true).maxAge(3600);
    }

    //JWT 인증필터 등록
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter>
    jwtAuthenticationFilterFilterRegistrationBean(){
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthenticationFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
