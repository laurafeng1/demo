package com.example.demo.exception;

public class PayRetryExeption extends  RuntimeException{
    public PayRetryExeption(String message) {
        super(message);
    }
}
