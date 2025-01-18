package com.example.demo.exception;

public class PayNoRetryException extends RuntimeException{
    public PayNoRetryException(String message){
        super(message);
    }
}
