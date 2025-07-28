package org.example.todorecapjava.controller;

public class TodoNotFound extends RuntimeException {
    public TodoNotFound(String message) {

        super(message);
    }
}
