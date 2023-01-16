package com.example.springbootapplicationtask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorObject {
    private int status;

    private String message;

    private LocalDateTime timestamp;
}
