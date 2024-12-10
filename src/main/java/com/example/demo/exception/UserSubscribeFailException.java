package com.example.demo.exception;

public class UserSubscribeFailException extends RuntimeException{
    public UserSubscribeFailException(String message) {
        super(message);
    }
}
