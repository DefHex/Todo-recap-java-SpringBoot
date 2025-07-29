package org.example.todorecapjava.controller;

import org.example.todorecapjava.dto.TodoDto;
import org.example.todorecapjava.model.Todo;
import org.example.todorecapjava.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService todoService) {
        this.service = todoService;
    }

     @GetMapping
     public List<Todo> getAllTodos() {
         return service.getAllTodos();
     }

     @GetMapping("/{id}")
     public Todo getTodoById(@PathVariable String id) {
         return service.getTodoById(id);
     }

     @PostMapping
     public ResponseEntity<Todo> createTodo(@RequestBody TodoDto todo) {
        return new ResponseEntity<>(service.createTodo(todo),HttpStatus.CREATED);
     }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDto todo, @PathVariable String id) {
        return new ResponseEntity<>(service.updateTodo(todo,id).orElse(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteTodo(id),HttpStatus.RESET_CONTENT);
    }
}
