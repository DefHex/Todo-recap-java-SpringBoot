package org.example.todorecapjava.controller;

import org.example.todorecapjava.dto.TodoDto;
import org.example.todorecapjava.model.Todo;
import org.example.todorecapjava.repository.TodoRepository;
import org.example.todorecapjava.service.IdService;
import org.example.todorecapjava.service.TodoService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.example.todorecapjava.model.Status.DONE;
import static org.example.todorecapjava.model.Status.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TodoControllerTest {
    Todo todo1 = new Todo("Learn Java", "1", OPEN);

    @Test
    void getTodoById_whenCorrectIdIsGiven() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        Optional<Todo> response = Optional.of(todo1);
        when(mockRepo.findById(todo1.id())).thenReturn(response);

        Todo actual=todoService.getTodoById(todo1.id());
        assertEquals(todo1, actual);
    }
    @Test
    void getTodoById_whenWrongIdIsGiven() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.getTodoById("WrongId"));
    }


    @Test
    void updateTodo_whenCorrectIdIsGiven() {
        Todo todoUpdated = new Todo("Learn Java", "1", DONE);
        TodoDto todoUpdatedDto = new TodoDto(todo1.description(), DONE);
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        Optional<Todo> response = Optional.of(todo1);
        when(mockRepo.findById(todo1.id())).thenReturn(response);
        when(mockRepo.save(todoUpdated)).thenReturn(todoUpdated);
        when(mockRepo.findById(todo1.id())).thenReturn(Optional.of(todoUpdated));

        Optional<Todo> actual=todoService.updateTodo(todoUpdatedDto,todo1.id());
        assertEquals(todoUpdated, actual.orElse(null));
    }

    @Test
    void updateTodo_whenWrongIdIsGiven() {
        TodoDto todoUpdatedDto = new TodoDto(todo1.description(), DONE);
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.updateTodo(todoUpdatedDto, "WrongId"));
    }

    @Test
    void deleteTodo_nonExistingTodo() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.deleteTodo("WrongId"));
    }
    @Test
    void deleteTodo_ExistingTodo() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        TodoService todoService = new TodoService(mockRepo, mockId);
        when(mockRepo.findById(todo1.id())).thenReturn(Optional.of(todo1));
        String expected = "Todo with id: " + todo1.id() + " deleted successfully.";
        String actual = todoService.deleteTodo(todo1.id());
        assertEquals(expected,actual);
        verify(mockRepo).deleteById(todo1.id());
    }
}