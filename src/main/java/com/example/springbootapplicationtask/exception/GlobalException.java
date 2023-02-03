package com.example.springbootapplicationtask.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupported httpRequestMethodNotSupported){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(httpRequestMethodNotSupported.getMessage());
        errorObject.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<ErrorObject> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("This should be application specific");
        errorObject.setTimestamp(LocalDateTime.now());
//        String bodyOfResponse = "This should be application specific";
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value =  HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorObject> handleConflict2(RuntimeException ex, WebRequest request) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage("This should be application specific");
        errorObject.setTimestamp(LocalDateTime.now());
//        String bodyOfResponse = "This should be application specific";
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }
}
