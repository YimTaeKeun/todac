package com.ytk.todac.todac.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.global.exception.BadRequestException;
import com.ytk.todac.todac.global.http.KakaoHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoWebSocialLoginService implements SocialLoginService{
    private final KakaoHttpClient kakaoHttpClient;
    @Override
    public UserAuthDTO fetchUserInfo(AuthRequestDTO authRequestDTO) {
        // 인가 코드 정보와 redirectUri를 조합하여 로그인
        String authCode = authRequestDTO.getToken();
        String redirectUri = authRequestDTO.getRedirectUri();
        if(authCode == null || redirectUri == null) throw new BadRequestException("로그인에 필요한 필수 파라미터 부재.");
        JsonNode kakaoTokenResponse = kakaoHttpClient.getToken(authCode, redirectUri);
        String accessToken = kakaoTokenResponse.get("access_token").asText();
        JsonNode kakaoUserInfoResponse = kakaoHttpClient.fetchUserInfo(accessToken);
        JsonNode kakaoAccount = kakaoUserInfoResponse.get("kakao_account");
        JsonNode profile = kakaoAccount.get("profile");
        String nickname = profile.get("nickname").asText();
        String id = kakaoUserInfoResponse.get("id").asText();
        return new UserAuthDTO(nickname, id);
    }
}
