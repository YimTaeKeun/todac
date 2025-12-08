package com.ytk.todac.todac.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private final String username; // 유저 이름 입니다.
    private final String socialUniqueId; // 소셜 서버에서 발급되는 유저 개인 번호 입니다.
    private final TokenInfo tokenInfo;

    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TokenInfo{
        private final String accessToken;
        private final String refreshToken;
    }
}
