package org.example.todorecapjava.service;

import org.example.todorecapjava.controller.WrongIdTodoNotFound;
import org.example.todorecapjava.dto.TodoDto;
import org.example.todorecapjava.model.Todo;
import org.example.todorecapjava.repository.TodoRepository;
import org.example.todorecapjava.service.GPTapiModel.Content;
import org.example.todorecapjava.service.GPTapiModel.Output;
import org.example.todorecapjava.service.GPTapiModel.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.example.todorecapjava.model.Status.DONE;
import static org.example.todorecapjava.model.Status.OPEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {
    Todo todo1 = new Todo("Learn Java", "1", OPEN);

    @Test
    void getTodoById_whenCorrectIdIsGiven() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);
        Optional<Todo> response = Optional.of(todo1);
        when(mockRepo.findById(todo1.id())).thenReturn(response);

        Todo actual=todoService.getTodoById(todo1.id());
        assertEquals(todo1, actual);
    }
    @Test
    void getTodoById_whenWrongIdIsGiven() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId, mockChatGPT);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.getTodoById("WrongId"));
    }


    @Test
    void updateTodo_whenCorrectIdIsGiven() {
        Todo todoUpdated = new Todo("Learn Java", "1", DONE);
        TodoDto todoUpdatedDto = new TodoDto(todo1.description(), DONE);
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);
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
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.updateTodo(todoUpdatedDto, "WrongId"));
    }

    @Test
    void deleteTodo_nonExistingTodo() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);
        assertThrows(WrongIdTodoNotFound.class, () -> todoService.deleteTodo("WrongId"));
    }
    @Test
    void deleteTodo_ExistingTodo() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);
        when(mockRepo.findById(todo1.id())).thenReturn(Optional.of(todo1));
        String expected = "Todo with id: " + todo1.id() + " deleted successfully.";
        String actual = todoService.deleteTodo(todo1.id());
        assertEquals(expected,actual);
        verify(mockRepo).deleteById(todo1.id());
    }

    @Test
    void generateId() {
        IdService idService = new IdService();
        String id = idService.generateId();

        // Check if the generated ID is not null or empty
        assertNotNull(id);
        assertFalse(id.isEmpty());
    }

    @Test
    void createTodo() {
        TodoDto todoDto = new TodoDto("Learn Java", DONE);
        IdService mockId = mock(IdService.class);
        when(mockId.generateId()).thenReturn("1");

        TodoRepository mockRepo = mock(TodoRepository.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId, mockChatGPT);

        Todo expectedTodo = new Todo(todoDto.description(), "1", todoDto.status());
        when(mockRepo.save(expectedTodo)).thenReturn(expectedTodo);
        Response gptResponse = new Response(
                List.of(new Output(List.of(new Content("Learn Java"))))
        );
        when(mockChatGPT.replaceSpellingMistakesWhenCreatingTodo(todoDto)).thenReturn(gptResponse);
        Todo actualTodo = todoService.createTodo(todoDto);

        assertEquals(expectedTodo, actualTodo);
        verify(mockRepo).save(expectedTodo);
    }

    @Test
    void getAllTodos() {
        TodoRepository mockRepo = mock(TodoRepository.class);
        IdService mockId = mock(IdService.class);
        ChatGPTService mockChatGPT = mock(ChatGPTService.class);
        TodoService todoService = new TodoService(mockRepo, mockId,mockChatGPT);

        when(mockRepo.findAll()).thenReturn(List.of(todo1));

        List<Todo> todos = todoService.getAllTodos();

        assertEquals(1, todos.size());
        assertEquals(todo1, todos.getFirst());
    }
}