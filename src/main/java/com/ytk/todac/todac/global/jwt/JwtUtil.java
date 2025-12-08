package com.ytk.todac.todac.global.jwt;

import com.ytk.todac.todac.global.config.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 해당 클래스는 JWT와 관련된 여러 작업을 담당합니다.
 * */
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfiguration conf;


    /**
     * 추가 클레임 정보를 바탕으로 JWT를 제작합니다.
     * 아래 클레임들은 포함되어 있으니 추가하지 않으셔도 됩니다.
     * <br/>
     * <p>claims</p>
     * 1. iss
     * 2. iat
     * */
    public String generateJwt(Claims additionalClaims) {
        setDefaultClaims(additionalClaims); // 기본 JWT 설정 클레임 저장
        Map<String, Object> headers = getJwtHeaders();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(conf.getSecretKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setClaims(additionalClaims)
                .setHeaderParams(headers)
                .compact();
    }

    private Map<String, Object> getJwtHeaders(){
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", conf.getTyp());
        headers.put("alg", conf.getAlg());
        return headers;
    }

    private void setDefaultClaims(Claims additionalClaims){
        Date now = new Date();
        additionalClaims.setIssuer(conf.getIssuer());
        additionalClaims.setIssuedAt(now);
    }
}
