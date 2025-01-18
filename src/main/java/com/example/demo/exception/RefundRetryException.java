package com.example.demo.exception;

public class RefundRetryException extends RuntimeException{
    public RefundRetryException(String message) {
        super(message);
    }
}
