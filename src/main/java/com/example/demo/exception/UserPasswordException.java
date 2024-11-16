package com.example.demo.exception;

public class UserPasswordException extends RuntimeException {
    public UserPasswordException(String message) {
        super(message);
    }
}
