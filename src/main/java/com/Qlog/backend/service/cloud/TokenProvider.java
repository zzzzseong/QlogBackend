package com.Qlog.backend.service.cloud;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${jwt-secret-key}") private String secretKey;
    @Value("${jwt-issuer}") private String issuer;
    @Value("${jwt-expiration}") private Long expiration;

    public String createJWT(String userSpec) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer) //Token 발행자
                .setIssuedAt(now) //Token 발행일자
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(expiration).toMillis())) //Token 만료시간설정
                .claim("email", userSpec) //비공개 Claim - Token 에 담을 정보
                .signWith(SignatureAlgorithm.HS256, secretKey) //해싱 알고리즘 및 시크릿 키 설정
                .compact();
    }

    public Claims parseJWT(String authorizationHeader) {
        String token = validationAuthorizationHeader(authorizationHeader);

        return Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
    }
    private String validationAuthorizationHeader(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        } else {
            return authorizationHeader.substring("Bearer ".length());
        }
    }
}