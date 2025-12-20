package com.ytk.todac.todac.global.http;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 카카오 서버와 통신을 하기위한 클래스 입니다. HttpConfiguration을 통해 설정된 WebClient를 통해 통신을 진행합니다.
 * */
@Component
@Setter
@Getter
public class KakaoHttpClient {
    private final WebClient apiClient;
    private final WebClient authClient;
    private final KakaoHttpResponseHandler kakaoHttpResponseHandler;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String kakaoClientSecret;
    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoClientId;


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
        WebClient.ResponseSpec RawResponse = apiClient.get()
                .uri(fetchUserUri) // uri를 설정합니다.
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .retrieve(); // 응답을 가져옵니다.
        // 오류 핸들링을 포함한 처리를 한 뒤 최종적인 응답을 가져옵니다.
        return kakaoHttpResponseHandler.handleResponse(RawResponse, JsonNode.class);
    }

    public JsonNode getToken(String code, String redirectUri) {
        String fetchTokenUri = "/oauth/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("redirect_uri", redirectUri);
        formData.add("client_id", kakaoClientId);
        formData.add("client_secret", kakaoClientSecret);
        WebClient.ResponseSpec rawResponse = authClient.post()
                .uri(fetchTokenUri)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(formData))
                .retrieve();
        return kakaoHttpResponseHandler.handleResponse(rawResponse, JsonNode.class);
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
