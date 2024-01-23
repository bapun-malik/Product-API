package com.amazon.product.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptions {
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail userNotFoundExceptionHandler(IOException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(FileNameContainsSpace.class)
    public ProblemDetail fileNotSupported(FileNameContainsSpace e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
    }
}
