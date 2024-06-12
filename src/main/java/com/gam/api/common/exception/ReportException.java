package com.gam.api.common.exception;

import org.springframework.http.HttpStatus;

public class ReportException extends GamException {
    public ReportException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
