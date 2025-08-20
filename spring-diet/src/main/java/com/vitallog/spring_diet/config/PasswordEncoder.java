package com.vitallog.spring_diet.config;

public interface PasswordEncoder {
    //유연성을 위해 rawPassword는 CharSequence로 받음
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodePassword);
}
