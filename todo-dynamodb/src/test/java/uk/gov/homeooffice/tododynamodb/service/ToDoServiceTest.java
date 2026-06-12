package uk.gov.homeooffice.tododynamodb.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;
import uk.gov.homeooffice.tododynamodb.repository.ToDoRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @MockBean
    private ToDoRepository repository;
    @Autowired
    private ToDoService toDoService;

    @BeforeEach
    void resetMocks() {
        reset(repository);
    }

    @Test
    void givenToDoCreation_ValidToDoCreatedAndSaved() {

        CreateToDoDTO newToDo = CreateToDoDTO.builder()
                        .title("Mow the lawn.")
                        .description("It's just too long. Stripes please.")
                        .due(LocalDateTime.now())
                        .assignee("Child one")
                        .build();

        var result = toDoService.createTodo(newToDo);

        assertNotNull(result.getId());

        // Verify the entity is saved as expected
        var expectedEntity = ToDoEntity.builder()
                        .id(result.getId().toString())
                        .title(newToDo.getTitle())
                        .description(newToDo.getDescription())
                        .due(newToDo.getDue().toString())
                        .assignee(newToDo.getAssignee())
                        .build();

        ArgumentCaptor<ToDoEntity> captor = ArgumentCaptor.forClass(ToDoEntity.class);

        verify(repository,times(1)).save(captor.capture());

        ToDoEntity saved = captor.getValue();
        assertEquals(expectedEntity, saved);
    }

    @Test
    void givenTodoWithNoDueDate_ValidToDoCreatedAndSaved() {

        CreateToDoDTO newToDo = CreateToDoDTO.builder()
                .title("Mow the lawn.")
                .description("It's just too long. Stripes please.")
                .due(null)
                .assignee("Child one")
                .build();

        var result = toDoService.createTodo(newToDo);

        assertNotNull(result.getId());

        // Verify the entity is saved as expected
        var expectedEntity = ToDoEntity.builder()
                .id(result.getId().toString())
                .title(newToDo.getTitle())
                .description(newToDo.getDescription())
                .due(null)
                .assignee(newToDo.getAssignee())
                .build();

        ArgumentCaptor<ToDoEntity> captor = ArgumentCaptor.forClass(ToDoEntity.class);

        verify(repository, times(1)).save(captor.capture());

        ToDoEntity saved = captor.getValue();
        assertEquals(expectedEntity, saved);
    }
}