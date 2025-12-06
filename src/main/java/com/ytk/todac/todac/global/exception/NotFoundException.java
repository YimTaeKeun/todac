package com.ytk.todac.todac.global.exception;


import org.slf4j.event.Level;

public class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(Level.INFO, message); // 로그 레벨을 INFO로 설정합니다.
    }
}
