package com.spring.authorization.testing.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "The authentication token has expired.");
        responseBody.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalException(Exception ex) {
        // Build error response
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", ex.getMessage());

        // Return HTTP response with error
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }
}
