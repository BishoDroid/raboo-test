package com.rabobank.betest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    String message;
    HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
