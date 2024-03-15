package com.app.ecoswap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotProductFoundException extends RuntimeException{

    public NotProductFoundException(String message) {
        super(message);
    }
}
