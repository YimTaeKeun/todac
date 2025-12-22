package com.ytk.todac.todac.global.jwt.token;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.global.config.JwtConfiguration;
import com.ytk.todac.todac.global.jwt.JwtUtil;
import com.ytk.todac.todac.global.jwt.token.refresh.RefreshToken;
import com.ytk.todac.todac.global.jwt.token.refresh.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID; // jti 생성 시 사용

import java.util.Date;

/**
 * 해당 클래스는 JWT 엑세스 토큰, 리프레시 토큰 발급을 담당합니다.
 * */
@Component
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager{
    private final JwtConfiguration conf;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Claims verifyToken(String token) {
        return jwtUtil.verifyJwt(token);
    }

    @Override
    public String issueRefreshToken(String oldRefreshToken) {
        return "";
    }


    @Override
    public String issueAccessToken(UserDTO userDTO) {
        return jwtUtil.generateJwt(getClaims(TOKEN_TYPE.ACCESS_TOKEN, userDTO));
    }

    @Override
    public String issueRefreshToken(UserDTO userDTO) {
        Claims claims = getClaims(TOKEN_TYPE.REFRESH_TOKEN, userDTO);
        String token = jwtUtil.generateJwt(claims);
        refreshTokenRepository.save(new RefreshToken(token, claims.getId()));
        return token;
    }

    private Claims getClaims(TOKEN_TYPE tokenType, UserDTO userDTO) {
        Date now = new Date();
        Date expTime;
        Claims claims = Jwts.claims();

        claims.setSubject(String.valueOf(userDTO.getId())); // 사용자 서버 pk를 식별자로

        if(tokenType == TOKEN_TYPE.ACCESS_TOKEN) expTime = new Date(now.getTime() + conf.getAccessTokenValidationPeriod());
        else expTime = new Date(now.getTime() + conf.getRefreshTokenValidationPeriod());

        claims.setExpiration(expTime); // 토큰 유효기간 설정
        if(tokenType == TOKEN_TYPE.REFRESH_TOKEN) {
            String jti;
            do{
                jti = UUID.randomUUID().toString();
            }while(refreshTokenRepository.findByJti(jti) != null);
            claims.setId(jti);
        }
        return claims;
    }

    private String getJtiValue(){
        return UUID.randomUUID().toString();
    }
}
