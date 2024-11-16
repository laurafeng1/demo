package com.example.demo.exception;

public class CourseNotExistException extends RuntimeException {
    public CourseNotExistException(String message) {
        super(message);
    }
}
