package org.example.todorecapjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongIdTodoNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFound(WrongIdTodoNotFound e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
