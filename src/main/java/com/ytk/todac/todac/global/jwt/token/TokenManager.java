package com.ytk.todac.todac.global.jwt.token;

import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import io.jsonwebtoken.Claims;

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
     * 토큰을 검증합니다.
     * @param token 검증할 토큰을 의미합니다.
     * */
    Claims verifyToken(String token, TOKEN_TYPE tokenType);

    /**
     * 리프레시 토큰을 DB에서 삭제시킵니다.
     * */
    void deleteRefreshToken(String token);
}
