package com.example.demo.exception;

public class CourseNotAvailableException extends RuntimeException {
    public CourseNotAvailableException(String message) {
        super(message);
    }
}
