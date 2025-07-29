package org.example.todorecapjava.service;

import org.example.todorecapjava.dto.TodoDto;
import org.example.todorecapjava.service.GPTapiModel.Request;
import org.example.todorecapjava.service.GPTapiModel.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChatGPTService {
    private final RestClient restClient;

    public ChatGPTService(RestClient.Builder restClient) {
        this.restClient = restClient
                .baseUrl("https://api.openai.com/v1/responses")
                .defaultHeader("Authorization","Bearer "+
                        System.getenv("OPENAI_API_KEY"))
                .build();
    }

    public Response replaceSpellingMistakesWhenCreatingTodo(TodoDto todo) {
        var request = new Request(
                "gpt-4.1",
                "Check the spelling mistakes in the following text and correct them. " +
                        "If there are no mistakes, return the original text. " +
                        "Respond only with the correct sentence. No additional description.",
                todo.description()
        );
         return restClient.post()
                        .body(request)
                        .retrieve()
                        .body(Response.class);
    }
}
