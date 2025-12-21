package com.ytk.todac.todac.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ytk.todac.todac.domain.auth.dto.AuthRequestDTO;
import com.ytk.todac.todac.domain.auth.dto.UserAuthDTO;
import com.ytk.todac.todac.global.exception.BadRequestException;
import com.ytk.todac.todac.global.http.NaverHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverWebSocialLoginService implements SocialLoginService{
    private final NaverHttpClient naverHttpClient;

    @Override
    public UserAuthDTO fetchUserInfo(AuthRequestDTO authRequestDTO) {
        String redirectUri = authRequestDTO.getRedirectUri();
        String code = authRequestDTO.getToken();
        if(code == null || redirectUri == null) throw new BadRequestException("로그인에 필요한 필수 파라미터 부재.");
        JsonNode tokenInfo = naverHttpClient.getToken(code, redirectUri);
        String accessToken = tokenInfo.get("access_token").asText();
        JsonNode response = naverHttpClient.fetchUserInfo(accessToken).get("response");
        String nickname = response.get("nickname").asText();
        String socialUniqueId = response.get("id").asText();
        return new UserAuthDTO(nickname, socialUniqueId);
    }
}
