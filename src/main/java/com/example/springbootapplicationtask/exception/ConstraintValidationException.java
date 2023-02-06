package com.example.springbootapplicationtask.exception;

public class ConstraintValidationException extends RuntimeException{
    public ConstraintValidationException(String message){
        super(message);
    }
}
