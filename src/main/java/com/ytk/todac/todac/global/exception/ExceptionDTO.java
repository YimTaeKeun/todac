package com.ytk.todac.todac.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * 해당 클래스는 예외 발생 시 클라이언트 측에 보여줄 메시지 형태를 정의합니다.
 * @author ytk03
 * @since 2025-12-05
 * */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null이 아닌 값만 사용자에게 띄워줍니다.
public class ExceptionDTO {
    public String exceptionCode;
    public String message;
    public String stackTrace;

    @Override
    public String toString() {
        return "ExceptionCode: " + exceptionCode + ", Message: " + message + ", StackTrace: " + stackTrace;
    }
}
