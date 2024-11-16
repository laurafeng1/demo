package com.example.demo.exception;

public class UserRegisterFailedException extends RuntimeException {
    public UserRegisterFailedException(String message) {
        super(message);
    }
}
