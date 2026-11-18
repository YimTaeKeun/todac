package com.ytk.todac.todac.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * 해당 클래스는 해당 서비스 서버에서 jwt 토큰을 발급할 때 그 속성을 설정하기 위한 클래스입니다.
 * */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {
    // jwt 키를 암호화 하기 위한 시크릿키입니다.
    private String secretKey;
    // 토큰 발급자를 의미합니다.
    private String issuer;

    // 알고리즘 유형
    private String alg;

    // 토큰 타입
    private String typ;

    // 액세스 토큰 유효기간
    private Long accessTokenValidationPeriod;

    // 리프레시 토큰 유효기간
    private Long refreshTokenValidationPeriod;
}
