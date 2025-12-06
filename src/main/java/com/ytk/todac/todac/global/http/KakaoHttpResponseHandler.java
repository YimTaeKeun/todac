package com.ytk.todac.todac.global.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.ytk.todac.todac.global.exception.CustomException;
import com.ytk.todac.todac.global.exception.HttpBadRequestException;
import com.ytk.todac.todac.global.exception.HttpExternalServerException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class KakaoHttpResponseHandler{
    /**
     * 예외 처리 후, 특정 타입 형식으로 반환을 합니다.
     * */
    public <T> T handleResponse(WebClient.ResponseSpec response, Class<T> responseType){
        // bodyToMono는 Mono<JsonNode>를 반환하는데, 우리가 최종적으로 반환해야하는 것은 에러를 담은 Mono.error이므로 flatMap을 사용함
        return response
                .onStatus(
                        HttpStatusCode::is4xxClientError, // 400번대 오류 핸들링
                        r -> r.bodyToMono(JsonNode.class).flatMap(
                                errorBody -> Mono.error(new HttpBadRequestException(errorBody.toString()))
                        )
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError, // 500번대 오류, 즉 카카오 오류라면
                        r -> r.bodyToMono(JsonNode.class).flatMap(
                                errorBody -> Mono.error(new HttpExternalServerException(errorBody.toString()))
                        )
                )
                .bodyToMono(responseType).block();
    }
}
