package com.ytk.todac.todac.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.global.http.KakaoHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAppSocialLoginService implements SocialLoginService {
    private final KakaoHttpClient kakaoHttpClient;
    @Override
    public UserAuthDTO fetchUserInfo(AuthRequestDTO authRequestDTO) {
        // 카카오 서버를 통하여 유저 정보를 가져옵니다.
        JsonNode response = kakaoHttpClient.fetchUserInfo(authRequestDTO.getToken());
        JsonNode kakaoAccount = response.get("kakao_account");
        JsonNode profile = kakaoAccount.get("profile");
        String nickname = profile.get("nickname").asText();
        String id = response.get("id").asText();
        return new UserAuthDTO(nickname, id);
    }
}
