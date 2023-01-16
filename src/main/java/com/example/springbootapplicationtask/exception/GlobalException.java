package com.example.springbootapplicationtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(resourceNotFoundException.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleNoDataFoundException(NoDataFoundException noDataFoundException){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NO_CONTENT.value());
        errorObject.setMessage(noDataFoundException.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.OK);
    }
}
