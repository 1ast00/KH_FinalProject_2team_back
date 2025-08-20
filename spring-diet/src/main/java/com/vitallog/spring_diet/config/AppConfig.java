package com.vitallog.spring_diet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //passwordEncoder()에서 반환하는 객체를 Spring bean으로 등록하기 위한 @Bean annotation
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CustomPasswordEncoder();
    }
}
