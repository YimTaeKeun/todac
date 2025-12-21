package com.ytk.todac.todac.global.http;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverHttpClient {
    private final WebClient authWebClient;
    private final WebClient apiWebClient;
    private final KakaoHttpResponseHandler kakaoHttpResponseHandler;

    @Value("${NAVER_CLIENT_ID}")
    private String clientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;

    public JsonNode getToken(String code, String redirectUri) {
        String fetchTokenUri = "/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("redirect_uri", redirectUri);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("state", "STATE_STRING");
        WebClient.ResponseSpec a = authWebClient.post()
                .uri(fetchTokenUri)
                .body(BodyInserters.fromFormData(formData))
                .retrieve();
        return kakaoHttpResponseHandler.handleResponse(a, JsonNode.class);
    }

    public JsonNode fetchUserInfo(String accessToken) {
        String fetchUserInfoUri = "/v1/nid/me";
        WebClient.ResponseSpec response = apiWebClient.get()
                .uri(fetchUserInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve();
        return kakaoHttpResponseHandler.handleResponse(response, JsonNode.class);
    }

    public NaverHttpClient(
            @Qualifier("naverAuthWebClient") WebClient authWebClient,
            @Qualifier("naverApiWebClient") WebClient apiWebClient,
            KakaoHttpResponseHandler kakaoHttpResponseHandler
    ) {
        this.authWebClient = authWebClient;
        this.kakaoHttpResponseHandler = kakaoHttpResponseHandler;
        this.apiWebClient = apiWebClient;
    }
}
