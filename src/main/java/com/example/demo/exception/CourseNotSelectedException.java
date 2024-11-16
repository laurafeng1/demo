package com.example.demo.exception;

public class CourseNotSelectedException extends RuntimeException {
    public CourseNotSelectedException(String message) {
        super(message);
    }
}
