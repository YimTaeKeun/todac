package com.ytk.todac.todac.domain.auth.service;

import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.AuthResponseDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.domain.usr.service.UserService;
import com.ytk.todac.todac.global.exception.NotFoundException;
import com.ytk.todac.todac.global.exception.UnAuthorizedException;
import com.ytk.todac.todac.global.jwt.JwtUtil;
import com.ytk.todac.todac.global.jwt.token.TOKEN_TYPE;
import com.ytk.todac.todac.global.jwt.token.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 해당 서비스는 회원가입과 로그인을 담당합니다.
 * 소셜 로그인, 세션 로그인 등 모든 로그인은 해당 클래스에서 처리합니다.
 *
 * @author ytk03
 * @since 2025-12-05
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginRegisterService {
    private final String SOCIAL_LOGIN_SERVICE_SUFFIX = "SocialLoginService";
    private final Map<String, SocialLoginService> socialLoginServices;
    private final UserService userService;
    private final TokenManager tokenManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO loginOrRegister(AuthRequestDTO authRequestDTO) {
        SocialLoginService SLS = getSocialLoginService(authRequestDTO.getService());
        UserAuthDTO dto = SLS.fetchUserInfo(authRequestDTO); // 토큰을 이용하여 사용자 정보 가져옴
        // 신규 회원이면 회원가입, 구 회원이면 유저 정보만 GET하여 돌려 보내기
        UserDTO userDTO;
        try{
            userDTO = userService.findBySocialUniqueId(authRequestDTO.getService(), dto.getSocialUniqueId());
        }catch (NotFoundException e){
            // 신규 회원이라면
            userDTO = registerUser(authRequestDTO.getService(), dto);
        }
        String accessToken = tokenManager.issueAccessToken(userDTO);
        String refreshToken = tokenManager.issueRefreshToken(userDTO);
        return AuthResponseDTO.builder()
                .username(userDTO.getUsername())
                .socialUniqueId(authRequestDTO.getService())
                .tokenInfo(AuthResponseDTO.TokenInfo.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .build();
    }

    public void logout(String refreshToken) {

        Claims claims = jwtUtil.verifyJwt(refreshToken); // 리프레시 토큰이 유효한가
        // 로그인한 사용자와 리프레시 토큰 소유자가 일치하는지 확인
        if(!userService.getLoginUser().getId().equals(Long.parseLong(claims.getSubject()))) {
            log.warn("제3자에 의한 리프레시 토큰 사용");
            throw new UnAuthorizedException("Invalid Refresh Token");
        }
        // 리프레시 토큰 삭제
        tokenManager.deleteRefreshToken(refreshToken);
    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        log.info("OldRefreshToken: {}", refreshToken);
        Claims claims = tokenManager.verifyToken(refreshToken, TOKEN_TYPE.REFRESH_TOKEN);
        Long userId = Long.parseLong(claims.getSubject());
        // 기존 리프레시 토큰 삭제
        tokenManager.deleteRefreshToken(refreshToken);
        UserDTO userDTO = userService.findById(userId);
        // 새로운 액세스 리프레시 토큰 발급
        String accessTokenString = tokenManager.issueAccessToken(userDTO);
        String refreshTokenString = tokenManager.issueRefreshToken(userDTO);
        return AuthResponseDTO.builder()
                .username(userDTO.getUsername())
                .socialUniqueId(userDTO.getRegisterService())
                .tokenInfo(AuthResponseDTO.TokenInfo.builder()
                        .accessToken(accessTokenString)
                        .refreshToken(refreshTokenString)
                        .build())
                .build();
    }

    private UserDTO registerUser(String registerService, UserAuthDTO userAuthDTO) {
        return userService.save(UserDTO.builder()
                .username(userAuthDTO.getUsername())
                .registerService(registerService)
                .socialUniqueId(userAuthDTO.getSocialUniqueId())
                .build());
    }

    private SocialLoginService getSocialLoginService(String serviceName) {
        SocialLoginService temp = socialLoginServices.get(serviceName + SOCIAL_LOGIN_SERVICE_SUFFIX);
        if(temp == null) throw new NotFoundException("Does Not Exist about " + serviceName + " service.");
        return temp;
    }
}
