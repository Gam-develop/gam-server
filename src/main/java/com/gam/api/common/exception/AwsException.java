package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class AwsException extends GamException {
    public AwsException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
