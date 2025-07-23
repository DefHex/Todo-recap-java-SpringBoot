package org.example.todorecapjava.service;

import org.example.todorecapjava.dto.TodoDto;
import org.example.todorecapjava.model.Status;
import org.example.todorecapjava.model.Todo;
import org.example.todorecapjava.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repo;
    private final IdService idService;

    public TodoService(TodoRepository todoRepository, IdService idService) {
        this.repo = todoRepository;
        this.idService = idService;
    }

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getTodoById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Todo createTodo(TodoDto todo) {
        Todo newTodo = new Todo(
                todo.description(),
                idService.generateId(),
                Status.valueOf(todo.status().toString())
        );
        return repo.save(newTodo);
    }

    public Optional<Todo> updateTodo(TodoDto todo, String id) {
        Todo oldTodo= repo.findById(id).orElse(null);
        repo.save(oldTodo.withStatus(Status.valueOf(todo.status().toString()))
                .withDescription(todo.description()));
        return repo.findById(id);
    }

    public String deleteTodo(String id) {
        if(repo.findById(id).isEmpty()) {
            return "Todo with id: " + id + " not found.";
        }
        repo.deleteById(id);
        return "Todo with id: " + id + " deleted successfully.";
    }
}
