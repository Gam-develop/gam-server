package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class BlockException extends GamException {
    public BlockException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}

