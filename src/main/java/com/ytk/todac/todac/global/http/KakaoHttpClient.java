package com.ytk.todac.todac.global.http;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 카카오 서버와 통신을 하기위한 클래스 입니다. HttpConfiguration을 통해 설정된 WebClient를 통해 통신을 진행합니다.
 * */
@Component
public class KakaoHttpClient {
    private final WebClient apiClient;
    private final WebClient authClient;
    private final KakaoHttpResponseHandler kakaoHttpResponseHandler;


    /**
     * 사용자 정보에 접근합니다.
     * 200 ok 응답 정보 (필수 정보 & 필요 정보만)
     * @param accessToken 액세스 토큰을 의미합니다.
     * @return JsonNode 형식
     * <h4>Return</h4>
     * id: 회원번호
     * <br/>
     * kakao_account: 카카오 계정 정보 (KakaoAccount)
     * <br/>
     * kakao_account.profile: 프로필 정보
     * <br/>
     * kakao_account.profile.nickname: 닉네임
     * <br/>
     * name: 카카오 계정 이름
     * */
    public JsonNode fetchUserInfo(String accessToken) {
        // 액세스 토큰을 이용하여 사용자 정보를 가져옵니다.
        String fetchUserUri = "/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        WebClient.ResponseSpec RawResponse = apiClient.get()
                .uri(fetchUserUri) // uri를 설정합니다.
                .retrieve(); // 응답을 가져옵니다.
        // 오류 핸들링을 포함한 처리를 한 뒤 최종적인 응답을 가져옵니다.
        return kakaoHttpResponseHandler.handleResponse(RawResponse, JsonNode.class);
    }



    public KakaoHttpClient(
            @Qualifier("kakaoApiWebClient") WebClient apiClient,
            @Qualifier("kakaoAuthWebClient") WebClient authClient,
            KakaoHttpResponseHandler kakaoHttpResponseHandler
    ) {
        this.apiClient = apiClient;
        this.authClient = authClient;
        this.kakaoHttpResponseHandler = kakaoHttpResponseHandler;
    }
}
