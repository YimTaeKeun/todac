package com.ytk.todac.todac.global.jwt.token;

import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.domain.usr.dto.UserDTO;

/**
 * 해당 인터페이스는 토큰 매니저의 역할을 정의합니다.
 * 해당 인터페이스는 토큰 매니저가 하는 역할인 액세스 토큰, 리프레시 토큰 발급을 정의합니다.
 * */
public interface TokenManager {
    /**
     * UserDTO를 바탕으로 액세스 토큰을 발급합니다.
     * @param userDTO 발급할 대상 user 정보입니다.
     * */
    String issueAccessToken(UserDTO userDTO);

    /**
     * UserDTO를 바탕으로 리프레쉬 토큰을 발급합니다.
     * @param userDTO 발급할 대상 user 정보입니다.
     * */
    String issueRefreshToken(UserDTO userDTO);


    /**
     * 기존 리프레시 토큰을 이용하여 새로운 리프레시 토큰을 발급합니다.
     * @param oldRefreshToken 기존 리프레시 토큰을 말합니다.
     * */
    String issueRefreshToken(String oldRefreshToken);
}
