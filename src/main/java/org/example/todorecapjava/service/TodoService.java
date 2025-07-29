package org.example.todorecapjava.service;

import org.example.todorecapjava.controller.WrongIdTodoNotFound;
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
    private final ChatGPTService chatGPTService;

    public TodoService(TodoRepository todoRepository, IdService idService, ChatGPTService chatGPTService) {
        this.repo = todoRepository;
        this.idService = idService;
        this.chatGPTService = chatGPTService;
    }

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getTodoById(String id) throws WrongIdTodoNotFound{
        return repo.findById(id)
                .orElseThrow(() -> new WrongIdTodoNotFound("Todo with id: " + id + " not found."));
    }

    public Todo createTodo(TodoDto todo) throws IllegalArgumentException {
        if (todo.description() == null || todo.description().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }

        String description=chatGPTService.replaceSpellingMistakesWhenCreatingTodo(todo)
                .output()
                .getFirst()
                .content()
                .getFirst()
                .text();

        Todo newTodo = new Todo(
                description.isBlank() ? todo.description() : description,
                idService.generateId(),
                Status.valueOf(todo.status().toString())
        );
        return repo.save(newTodo);
    }

    public Optional<Todo> updateTodo(TodoDto todo, String id) throws WrongIdTodoNotFound {
        Todo oldTodo= repo.findById(id)
                .orElseThrow(() -> new WrongIdTodoNotFound("Todo with id: " + id + " not found."));
        repo.save(oldTodo.withStatus(Status.valueOf(todo.status().toString()))
                .withDescription(todo.description()));
        return repo.findById(id);
    }

    public String deleteTodo(String id) throws WrongIdTodoNotFound{
        if(repo.findById(id).isEmpty()) {
            throw new WrongIdTodoNotFound("Todo with id: " + id + " not found.");
        }
        repo.deleteById(id);
        return "Todo with id: " + id + " deleted successfully.";
    }
}
