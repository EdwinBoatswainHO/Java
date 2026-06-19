package uk.gov.homeooffice.tododynamodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void canCreateToDo() throws Exception {
        String todoText = """
                {
                    "title": "Write A ToDo App",
                    "description": "Should work with this backend",
                    "assignee": "Edwin",
                    "due" : "2026-06-30T09:00"
                }
                """;
        MvcResult result = mockMvc.perform(post("/todo/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoText))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        ToDoDTO returned = objectMapper.readValue(response, ToDoDTO.class);

        assertEquals("Write A ToDo App", returned.getTitle());
        assertEquals("Should work with this backend", returned.getDescription());
        assertEquals("Edwin", returned.getAssignee());
        assertEquals("2026-06-30T09:00", returned.getDue().toString());
        assertNotNull(returned.getCreatedAt());

    }
}