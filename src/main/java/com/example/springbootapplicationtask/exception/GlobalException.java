package com.example.springbootapplicationtask.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalException{

    // occur when search appear none results, where it should exist, 404 error code
    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(resourceNotFoundException.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
    // occur when search appear none results, 204 error code
    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException noDataFoundException){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NO_CONTENT.value());
        errorObject.setMessage(noDataFoundException.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NO_CONTENT);
    }


    //    Invoked by jpa validation annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }
}
