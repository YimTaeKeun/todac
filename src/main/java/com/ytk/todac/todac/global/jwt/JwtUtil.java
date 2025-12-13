package com.ytk.todac.todac.global.jwt;

import com.ytk.todac.todac.global.config.JwtConfiguration;
import com.ytk.todac.todac.global.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.ytk.todac.todac.global.exception.BadRequestException;

import javax.crypto.SecretKey;
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
    private final JwtVerifier jwtVerifier = new JwtVerifier();


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
    /**
     * JWT를 검증합니다. 토큰이 변조되지 않았음을 검증합니다.
     * @throws BadRequestException 토큰 검증 실패시 발생하는 예외입니다.
     * @return 검증이 완료된 페이로드를 반환합니다.
     * @param jwt 검증할 jwt 토큰을 말합니다.
     * */
    public Claims verifyJwt(String jwt) {
        return jwtVerifier.verifyJwt(jwt);
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

    private class JwtVerifier{

        /**
         * JWT를 검증합니다. 토큰이 변조되지 않았음을 검증합니다.
         * @throws BadRequestException 토큰 검증 실패시 발생하는 예외입니다.
         * @param jwt 검증할 jwt 토큰을 말합니다.
         * */
        public Claims verifyJwt(String jwt) {
            // 1. 서명 검증
            Claims claims = verifySignature(jwt);
            // 2. 내부 값 검증
            verifyPayload(claims);
            return claims;
        }

        private Claims verifySignature(String jwt) {
            // 서명, 만료시간, 발행시간 자동 검증
            SecretKey secretKey = Keys.hmacShaKeyFor(conf.getSecretKey().getBytes(StandardCharsets.UTF_8));
            Claims claims;
            try{
                claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
            } catch (JwtException e){
                throw new UnAuthorizedException(e.getMessage());
            }
            return claims;
        }

        private void verifyPayload(Claims claims){
            try{
                // iss 검증
                if(!claims.getIssuer().equals(conf.getIssuer())) throw new UnAuthorizedException("Invalid");

            }catch (Exception e){
                throw new UnAuthorizedException("Invalid payload");
            }
        }
    }
}
