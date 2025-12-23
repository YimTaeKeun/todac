package com.ytk.todac.todac.global.jwt.token;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.global.config.JwtConfiguration;
import com.ytk.todac.todac.global.exception.UnAuthorizedException;
import com.ytk.todac.todac.global.exception.ValidationException;
import com.ytk.todac.todac.global.jwt.JwtUtil;
import com.ytk.todac.todac.global.jwt.token.blackList.BlackListToken;
import com.ytk.todac.todac.global.jwt.token.blackList.BlackListTokenRepository;
import com.ytk.todac.todac.global.jwt.token.refresh.RefreshToken;
import com.ytk.todac.todac.global.jwt.token.refresh.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
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
    public Claims verifyToken(String token, TOKEN_TYPE tokenType) {
        if(tokenType.equals(TOKEN_TYPE.REFRESH_TOKEN)){
            // 리프레시 토큰이 DB에 있는지 확인
            RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(token);
            if(refreshTokenEntity == null) throw new UnAuthorizedException("INVALID");
        }
        return jwtUtil.verifyJwt(token);
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

    @Override
    public void deleteRefreshToken(String refreshToken) {
        // 토큰을 블랙리스트 목록에 추가합니다.
        Claims claims = jwtUtil.verifyJwt(refreshToken); // 일단 유효한 토큰인지부터 확인
        // 토큰이 리프레시 토큰 목록에 있는지 확인
        RefreshToken tokenEntity = refreshTokenRepository.findByJti(claims.getId());
        if(tokenEntity == null) throw new ValidationException("Invalid refresh token");
        refreshTokenRepository.delete(tokenEntity); // 토큰 엔티티 삭제
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
                jti = getJtiValue();
            }while(refreshTokenRepository.findByJti(jti) != null);
            claims.setId(jti);
        }
        return claims;
    }

    private String getJtiValue(){
        return UUID.randomUUID().toString();
    }
}
