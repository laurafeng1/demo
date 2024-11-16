package com.example.demo.exception;

public class UserPasswordInvalidException extends RuntimeException {
    public UserPasswordInvalidException(String message) {
        super(message);
    }
}
