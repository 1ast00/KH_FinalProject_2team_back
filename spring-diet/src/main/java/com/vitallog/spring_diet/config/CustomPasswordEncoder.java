package com.vitallog.spring_diet.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CustomPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            //MessageDigest: hash값 계산 객체
            //getInstance("SHA-256"): SHA-256 알고리즘을 사용하는 MessageDigest 객체 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //rawPassword.toString(): CharSequence -> String
            //.getBytes(): String -> byte array
            //digest.digest(): byte array -> hash value(also byte array)
            byte[] hash = digest.digest(rawPassword.toString().getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        return encode(rawPassword).equals(encodePassword);
    }
}
