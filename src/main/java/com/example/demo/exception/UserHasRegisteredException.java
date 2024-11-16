package com.example.demo.exception;

public class UserHasRegisteredException extends RuntimeException {
    public UserHasRegisteredException(String message) {
        super(message);
    }
}
