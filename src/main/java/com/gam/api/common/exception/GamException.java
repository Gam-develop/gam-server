package com.gam.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GamException extends RuntimeException{
    private final HttpStatus statusCode;

    public GamException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
