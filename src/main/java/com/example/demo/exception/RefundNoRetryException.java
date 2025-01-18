package com.example.demo.exception;

public class RefundNoRetryException extends RuntimeException{
    public RefundNoRetryException(String message) {
        super(message);
    }
}
