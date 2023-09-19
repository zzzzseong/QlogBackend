package com.Qlog.backend.exception;

import com.Qlog.backend.exception.exceptions.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(401).body(new ErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }
}
