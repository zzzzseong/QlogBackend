package com.Qlog.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleException(CustomException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(400).body(new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }
}
