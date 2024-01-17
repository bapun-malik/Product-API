package com.amazon.product.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptions {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundExceptionHandler(IOException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FileNameContainsSpace.class)
    public ResponseEntity<String> fileNotSupported(FileNameContainsSpace e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
