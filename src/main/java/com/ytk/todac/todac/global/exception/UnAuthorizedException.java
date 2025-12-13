package com.ytk.todac.todac.global.exception;

import org.slf4j.event.Level;
/**
 * 사용자 인증에 실패했을 때 발생하는 예외입니다.
 * */
public class UnAuthorizedException extends CustomException {
    public UnAuthorizedException(String message) {
        super(Level.INFO, message);
    }
}
