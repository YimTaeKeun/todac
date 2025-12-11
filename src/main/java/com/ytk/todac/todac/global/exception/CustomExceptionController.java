package com.ytk.todac.todac.global.exception;

import com.ytk.todac.todac.global.log.LogHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * 해당 클래스는 예외 처리에 관한 로직을 담당합니다.
 * */
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionController {
    private final Environment environment;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildExceptionDTO("BAD_REQUEST", ex));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildExceptionDTO("INVALID_REQUEST", ex));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notfoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildExceptionDTO("NOT_FOUND", ex));
    }

    @ExceptionHandler(HttpBadRequestException.class)
    public ResponseEntity<?> httpBadRequestException(HttpBadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildExceptionDTO("BAD_REQUEST", ex));
    }

    @ExceptionHandler(HttpExternalServerException.class)
    public ResponseEntity<?> httpExternalServerException(HttpBadRequestException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildExceptionDTO("EXTERNAL_SERVER_ERROR", ex));
    }

    @ExceptionHandler(Exception.class) // 예상치 못한 예외가 발생했을 때
    public ExceptionDTO handleException(Exception ex){
        ExceptionDTO dto = buildExceptionDTO("INTERNAL SERVER ERROR", ex);
        LogHandler.printLog(Level.ERROR, dto.toString()); // 예상치 못한 예외 이므로 ERROR 처리
        LogHandler.printLog(Level.ERROR, Arrays.toString(ex.getStackTrace()));
        return dto;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildExceptionDTO("INTERNAL_SERVER_EXCEPTION", ex));
    }

    private ExceptionDTO buildExceptionDTO(String exceptionCode, Exception ex){
        return ExceptionDTO.builder()
                .exceptionCode(exceptionCode)
                .stackTrace((environment.getActiveProfiles()[0].equals("dev")) ? Arrays.toString(ex.getStackTrace()) : null)
                .message(ex.getMessage())
                .build();
    }
}
