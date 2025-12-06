package com.ytk.todac.todac.global.exception;

import com.ytk.todac.todac.global.log.LogHandler;
import org.slf4j.event.Level;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

/**
 * 해당 클래스는 예외 처리에 관한 로직을 담당합니다.
 * */
@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ExceptionDTO notfoundException(NotFoundException ex){
        return buildExceptionDTO("NOT_FOUND", ex);
    }

    @ExceptionHandler(Exception.class) // 예상치 못한 예외가 발생했을 때
    public ExceptionDTO handleException(Exception ex){
        ExceptionDTO dto = buildExceptionDTO("INTERNAL SERVER ERROR", ex);
        LogHandler.printLog(Level.ERROR, dto.toString()); // 예상치 못한 예외 이므로 ERROR 처리
        return dto;
    }

    @ExceptionHandler(CustomException.class)
    public ExceptionDTO handleCustomException(CustomException ex){
        return buildExceptionDTO("INTERNAL_SERVER_EXCEPTION", ex);
    }

    private ExceptionDTO buildExceptionDTO(String exceptionCode, Exception ex){
        return ExceptionDTO.builder()
                .exceptionCode(exceptionCode)
                .stackTrace(Arrays.toString(ex.getStackTrace()))
                .message(ex.getMessage())
                .build();
    }
}
