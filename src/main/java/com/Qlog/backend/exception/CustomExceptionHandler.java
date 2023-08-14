package com.Qlog.backend.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public CustomErrorResponse handleException(CustomException e) {
        return new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage());
    }
}
