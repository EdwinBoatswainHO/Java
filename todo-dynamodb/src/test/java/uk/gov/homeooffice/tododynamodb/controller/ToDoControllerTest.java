package uk.gov.homeooffice.tododynamodb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.gov.homeooffice.tododynamodb.repository.ToDoRepository;


@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ToDoRepository repository;

    @BeforeEach
    void setup() { repository.clear(); }

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

    @Test
    void canCreateAndRetrieve() throws Exception {
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

        // Lets ask for it by name
        result = mockMvc.perform(get("/todo/get/" + returned.getId()))
                .andExpect(status().isOk())
                .andReturn();
        response = result.getResponse().getContentAsString();

        ToDoDTO retrieved = objectMapper.readValue(response, ToDoDTO.class);
        assertEquals(returned, retrieved);
    }

    @Test
    void whenIdNotExisting_404Returned() throws Exception {
        var id = UUID.randomUUID();

        mockMvc.perform(get("/todo/get/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void canRetrieveAllToDos() throws Exception {
        var requests = IntStream.rangeClosed(1, 10)
                .mapToObj(
                        i -> CreateToDoDTO.builder()
                                .title("Title " + i)
                                .description("Desc + " + i)
                                .assignee("me")
                                .build()
                )
                .toList();

        requests.stream().forEach(r -> {
            try {
                mockMvc.perform(post("/todo/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(r)))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        var result = mockMvc.perform(get("/todo/get-all"))
                .andExpect(status().isOk())
                .andReturn();
        var contents = result.getResponse().getContentAsString();

        var todos = objectMapper.readValue(contents, new TypeReference<List<ToDoDTO>>() {});
        assertEquals(10, todos.size());
        // if I was minded I'd check each one...
    }

    @Test
    void canUpdateToDo() throws Exception {
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

        // Update
        returned.setDue(LocalDateTime.now());
        mockMvc.perform(put("/todo/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returned)))
                .andExpect(status().isOk());

        // Lets ask for it by name
        result = mockMvc.perform(get("/todo/get/" + returned.getId()))
                .andExpect(status().isOk())
                .andReturn();
        response = result.getResponse().getContentAsString();

        ToDoDTO retrieved = objectMapper.readValue(response, ToDoDTO.class);
        assertEquals(returned, retrieved);
    }

    @Test
    void whenIdNotExisting_update_404Returned() throws Exception {
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

        // invalid id and update
        returned.setId(UUID.randomUUID());
        mockMvc.perform(put("/todo/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returned)))
                .andExpect(status().isNotFound());
    }

    @Test
    void canDeleteById() throws Exception {
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

        mockMvc.perform(delete("/todo/delete/" + returned.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void whereIdNotExisting_delete_404Returned() throws Exception{
        mockMvc.perform(delete("/todo/delete/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}