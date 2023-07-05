package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class WorkException extends GamException {
    public WorkException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
