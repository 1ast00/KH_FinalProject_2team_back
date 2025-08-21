package com.vitallog.spring_diet.util;

import com.vitallog.spring_diet.dto.MemberDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;
    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    private Key key;

    @PostConstruct
    private void init(){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    //AccessToken 생성
    public String generateAccessToken(MemberDTO member){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder().setSubject(String.valueOf((member.getUserid())))
                .claim("roles", member.getRole())
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    //RefreshToken 생성
    public String generateRefreshToken(MemberDTO member){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationMs);

        return Jwts.builder().setSubject(String.valueOf((member.getUserid())))
                .claim("roles", member.getRole())
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    //토큰 사용자 아이디 추출(subject)
    public String getUseridFromToken(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getSubject();
    }

    //토큰 사용자 역할 추출(roles)
    public String getRolesFromToken(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().get("roles", String.class);
    }

    //토큰 유효성 검사
    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e){
            System.out.println("유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            System.out.println("JWT 클레임 문자열이 비어있습니다.");
        }
        return false;
    }

    public long getRefreshTokenExpirationMs(){
        return refreshTokenExpirationMs;
    }
}
