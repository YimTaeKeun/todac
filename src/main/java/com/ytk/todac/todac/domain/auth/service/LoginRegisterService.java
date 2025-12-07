package com.ytk.todac.todac.domain.auth.service;

import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LoginRegisterService {
    private final String SOCIAL_LOGIN_SERVICE_SUFFIX = "SocialLoginService";
    Map<String, SocialLoginService> socialLoginServices;

    public void loginOrRegister(AuthRequestDTO authRequestDTO) {
        SocialLoginService SLS = getSocialLoginService(authRequestDTO.getService());
        UserAuthDTO dto = SLS.fetchUserInfo(authRequestDTO.getToken()); // 토큰을 이용하여 사용자 정보 가져옴
        // 신규 회원이면 회원가입, 구 회원이면 유저 정보만 GET하여 돌려 보내기
    }

    private SocialLoginService getSocialLoginService(String serviceName) {
        SocialLoginService temp = socialLoginServices.get(serviceName + SOCIAL_LOGIN_SERVICE_SUFFIX);
        if(temp == null) throw new NotFoundException("Does Not Exist about " + serviceName + " service.");
        return temp;
    }
}
