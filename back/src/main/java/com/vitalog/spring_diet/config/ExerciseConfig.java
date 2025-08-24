package com.vitalog.spring_diet.config;

import org.springframework.context.annotation.Bean; // 메서드가 반환하는 객체를 스프링 Bean으로 등록
import org.springframework.context.annotation.Configuration; // 이 클래스가 설정 파일임을 선언

import org.springframework.http.converter.StringHttpMessageConverter; // HTTP 메시지를 문자열로 변환하는 클래스
import org.springframework.web.client.RestTemplate; // 외부 API 통신에 사용하는 HTTP 클라이언트

import java.nio.charset.StandardCharsets; // 문자 인코딩 표준(UTF-8 등)을 정의 -> 이거 데이터 한글 깨짐

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