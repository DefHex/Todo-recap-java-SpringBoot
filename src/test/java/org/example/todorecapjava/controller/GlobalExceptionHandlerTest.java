package org.example.todorecapjava.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void notFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        String message = "Todo not found";
        WrongIdTodoNotFound exception = new WrongIdTodoNotFound(message);

        String response = handler.NotFound(exception);

        assertEquals(message, response);
    }

    @Test
    void handleException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        String message = "Random exception occurred";
        Exception exception = new Exception(message);

        String response = handler.handleException(exception);

        assertEquals(message, response);
    }
}