package com.ytk.todac.todac.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    @Schema(description = "서비스 이름을 말합니다. 가능 리스트: kakaoApp, naver")
    private String service;

    @Schema(description = "로그인을 하기 위한 토큰 정보를 말합니다.")
    private String token;
}
