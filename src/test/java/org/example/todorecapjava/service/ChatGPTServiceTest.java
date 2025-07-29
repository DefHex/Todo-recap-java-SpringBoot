package org.example.todorecapjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ChatGPTServiceTest {
//    @Autowired
//    private MockMvc mockMvc;
    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void replaceSpellingMistakesWhenCreatingTodo() {
        // Given
        mockServer.expect(requestTo("https://api.openai.com/v1/responses"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                    """
                    {
                        "output":[
                            {
                                "choices": [
                                    {
                                        "text": "Create a todo for buying groceries"
                                    }
                                ]
                            }
                        ]
                    }
                    """
                        , MediaType.APPLICATION_JSON));

//        mockMvc.perform(MockMvcRequestBuilders.post("/api/"))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().json(
//                        """
//                        {
//                         "description": "Create a  for buying groceries",
//                         "id": "1",
//                         "status": "OPEN"
//                        }
//                        """
//                ));
    }
}