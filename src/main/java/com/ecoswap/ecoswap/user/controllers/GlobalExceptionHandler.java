package com.ecoswap.ecoswap.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, ?>> exceptionHanlder(Exception ex){
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", "Forbidden");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("code", "401");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
