package com.app.ecoswap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotUserFoundException extends RuntimeException{

    public NotUserFoundException(String message) {
        super(message);
    }
}
