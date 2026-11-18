package com.ytk.todac.todac.global.exception;

import org.slf4j.event.Level;

public class HttpExternalServerException extends CustomException {
    public HttpExternalServerException(String message) {
        // 외부 의존하고 있는 API 서버의 오류이므로 로그레벨을 WARN으로 설정
        super(Level.WARN, message);
    }
}
