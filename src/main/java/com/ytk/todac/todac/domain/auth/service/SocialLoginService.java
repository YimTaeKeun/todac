package com.ytk.todac.todac.domain.auth.service;

import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;

/**
 * 소셜 로그인 서비스에서 필수적으로 제공되어야 하는 메서드를 정의합니다.
 * 해당 인터페이스를 상속받을 때 반드시 "[서비스 이름]SocialLoginService"로 명명되어야 합니다.
 * @author ytk03
 * @since 2025-12-06
 * */
public interface SocialLoginService {
    /**
     * 소셜 서버를 통해 사용자 정보를 가져옵니다. UserAuthDTO 형식에 맞춰서 리턴 값을 받아옵니다.
     * @since 2025-12-06
     * */
    UserAuthDTO fetchUserInfo(String token);
}
