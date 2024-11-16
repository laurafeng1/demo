package com.example.demo.exception;

public class StudentNotExistException extends RuntimeException {
    public StudentNotExistException(String message) {
        super(message);
    }
}
