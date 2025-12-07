package com.ytk.todac.todac.global.http;

import com.ytk.todac.todac.global.exception.HttpBadRequestException;
import com.ytk.todac.todac.global.exception.HttpExternalServerException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoHttpResponseHandler{
    /**
     * 예외 처리 후, 특정 타입 형식으로 반환을 합니다.
     * */
    public <T> T handleResponse(WebClient.ResponseSpec response, Class<T> responseType){
        // bodyToMono는 Mono<JsonNode>를 반환하는데, 우리가 최종적으로 반환해야하는 것은 에러를 담은 Mono.error이므로 flatMap을 사용함
        return response
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .map(body -> new HttpBadRequestException(resp.statusCode().value() + ": " + body)))
                .onStatus(HttpStatusCode::is5xxServerError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .map(body -> new HttpExternalServerException(resp.statusCode().value() + ": " + body)))
                .bodyToMono(responseType).block();
    }
}
