package com.example.demo.exception;

public class UserNotExistedException extends RuntimeException {
    public UserNotExistedException(String message) {
        super(message);
    }
}
