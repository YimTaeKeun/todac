package com.ytk.todac.todac.global.exception;

import org.slf4j.event.Level;

/**
 * 해당 예외 클래스는 사용자에 의해 잘못 요청이 왔을 때 발생하는 예외입니다.
 * */
public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(Level.INFO, message);
    }
}
