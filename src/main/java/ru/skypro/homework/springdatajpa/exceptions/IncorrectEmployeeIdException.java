package ru.skypro.homework.springdatajpa.exceptions;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class IncorrectEmployeeIdException extends RuntimeException {
    public IncorrectEmployeeIdException(String message) {
        super(message);
    }
    private HttpStatus httpStatus;

    public IncorrectEmployeeIdException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
