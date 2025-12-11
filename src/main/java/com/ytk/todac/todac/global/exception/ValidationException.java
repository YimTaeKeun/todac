package com.ytk.todac.todac.global.exception;

import org.slf4j.event.Level;

public class ValidationException extends CustomException {
    public ValidationException(String message) {
        super(Level.INFO, message);
    }
}
