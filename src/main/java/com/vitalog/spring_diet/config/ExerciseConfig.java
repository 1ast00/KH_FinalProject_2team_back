package com.vitalog.spring_diet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class ExerciseConfig {

    @Bean
    // RestTemplate 객체를 생성 -> Bean으로 제공하는 메서드
    public RestTemplate restTemplate() {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();
        // RestTemplate의 메시지 변환기 목록 가져옴
        restTemplate.getMessageConverters()
                // 한글 깨짐 방지를 위해 UTF-8 변환기를 맨 앞에 추가
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // 설정이 완료된 객체 반환
        return restTemplate;
    }
}