package com.ytk.todac.todac.global.exception;

import com.ytk.todac.todac.global.log.LogHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

/**
 * 해당 클래스는 대표 예외 클래스이며, 모든 커스텀 예외는 해당 커스텀 예외를 상속하여 사용합니다.
 * @author ytk03
 * @since 2025-12-06
 * */
@Slf4j
public class CustomException extends RuntimeException {
    /**
     * @param logLevel 로그레벨을 의미합니다.
     * */
    public CustomException(Level logLevel, String message) {
        super(message);
        LogHandler.printLog(logLevel, message); // 로그를 출력합니다.
    }
}
