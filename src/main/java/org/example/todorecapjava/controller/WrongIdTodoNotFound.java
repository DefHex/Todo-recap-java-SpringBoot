package org.example.todorecapjava.controller;

public class WrongIdTodoNotFound extends RuntimeException {
    public WrongIdTodoNotFound(String message) {
        super(message);
    }
}
