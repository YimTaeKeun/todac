package com.ytk.todac.todac.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 외부 서버와 직접 송수신을 관리하기 위한 클래스 입니다. 외부 API 호출 관련 설정은 해당 클래스에서 직접 설정합니다.
 * */
@Configuration
public class HttpConfiguration {
    private static final String KAKAO_API_BASE_URL = "https://kapi.kakao.com";
    private static final String KAKAO_AUTH_API_BASE_URL = "https://kauth.kakao.com";

    private static final String NAVER_API_BASE_URL = "https://openapi.naver.com";
    private static final String NAVER_AUTH_API_BASE_URL = "https://nid.naver.com/oauth2.0";

    @Bean
    public WebClient kakaoApiWebClient() {
        return WebClient.builder()
                .baseUrl(KAKAO_API_BASE_URL)
                .build();
    }

    @Bean
    public WebClient kakaoAuthWebClient() {
        return WebClient.builder()
                .baseUrl(KAKAO_AUTH_API_BASE_URL)
                .build();
    }

    @Bean
    public WebClient naverAuthWebClient() {
        return WebClient.builder()
                .baseUrl(NAVER_AUTH_API_BASE_URL)
                .build();
    }

    @Bean
    public WebClient naverApiWebClient() {
        return WebClient.builder()
                .baseUrl(NAVER_API_BASE_URL)
                .build();
    }
}
