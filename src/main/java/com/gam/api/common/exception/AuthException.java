package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends GamException {
    public AuthException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
