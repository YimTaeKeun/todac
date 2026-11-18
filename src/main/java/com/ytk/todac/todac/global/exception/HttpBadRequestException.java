package com.ytk.todac.todac.global.exception;

import org.slf4j.event.Level;

/**
 * 해당 예외는 외부 API와 소통 중에 발생하는 400번대 예외이며, 클라이언트 혹은 사용자가 잘못된 요청을 보냈을 때 발생하며,
 * 해당 예외 책임은 서비스 서버가 아닌, 클라이언트 혹은 사용자에게 있음을 나타냅니다.
 * 개발 중에는 로그 레벨을 WARN으로 두며, 검증이 완료된 Production 버전에서는 INFO로 설정합니다.
 * */
public class HttpBadRequestException extends CustomException {
    public HttpBadRequestException(String message) {
        super(Level.WARN, message);
    }
}
