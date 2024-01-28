package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class ScrapException extends GamException{

    public ScrapException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
