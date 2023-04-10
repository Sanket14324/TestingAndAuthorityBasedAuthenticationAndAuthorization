package com.spring.authorization.testing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        // Build error response
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", "Access denied. You do not have the necessary permissions.");

        // Return HTTP response with error
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }

//    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
//    public ResponseEntity<Object> handleInvalidRequest(NoSuchRequestHandlingMethodException ex, WebRequest request)
//    { String message = "The requested URL is invalid or the resource does not exist.";
//        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); }

}
