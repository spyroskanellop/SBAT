package com.example.springbootapplicationtask.exception;

public class HttpRequestMethodNotSupported extends RuntimeException{

    public HttpRequestMethodNotSupported(String message){
        super(message);
    }
}
