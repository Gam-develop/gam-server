package com.gam.api.common;


import com.gam.api.common.exception.AuthException;
import com.gam.api.common.exception.AwsException;
import com.gam.api.common.exception.BlockException;
import com.gam.api.common.exception.WorkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static com.gam.api.common.message.ExceptionMessage.EMPTY_METHOD_ARGUMENT;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiResponse response = ApiResponse.fail(EMPTY_METHOD_ARGUMENT.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse> authException(AuthException exception){
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AwsException.class)
    public ResponseEntity<ApiResponse> awsException(AwsException exception){
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkException.class)
    public ResponseEntity<ApiResponse> workException(WorkException exception){
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlockException.class)
    public ResponseEntity<ApiResponse> BlockException(BlockException exception){
        ApiResponse response = ApiResponse.fail(exception.getMessage());
        return new ResponseEntity<>(response, exception.getStatusCode());
    }
}