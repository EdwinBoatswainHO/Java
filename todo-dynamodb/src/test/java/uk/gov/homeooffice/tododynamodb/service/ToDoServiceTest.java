package uk.gov.homeooffice.tododynamodb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;
import uk.gov.homeooffice.tododynamodb.repository.ToDoRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private ToDoRepository toDoRepository;

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

        var result = toDoService.createToDo(newToDo);

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
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void givenTodoWithNoDueDate_ValidToDoCreatedAndSaved() {

        CreateToDoDTO newToDo = CreateToDoDTO.builder()
                .title("Mow the lawn.")
                .description("It's just too long. Stripes please.")
                .due(null)
                .assignee("Child one")
                .build();

        var result = toDoService.createToDo(newToDo);

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
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void givenIdDoesNotExist_throwsIllegalArgumentException() {

        when(repository.retrieve(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> toDoService.retrieveById(UUID.randomUUID()));

    }

    @Test
    void givenIdDoesExist_ToDoIsRetrieved() {
        ToDoDTO expected = ToDoDTO.builder()
                .id(UUID.randomUUID())
                .title("Mow the lawn.")
                .description("It's just too long. Stripes please.")
                .due(LocalDateTime.now())
                .assignee("Child one")
                .createdAt(LocalDateTime.now())
                .build();
        var fetchedEntity = ToDoEntity.builder()
                .id(expected.getId().toString())
                .title(expected.getTitle())
                .description(expected.getDescription())
                .due(expected.getDue().toString())
                .assignee(expected.getAssignee())
                .createdAt(expected.getCreatedAt().toString())
                .build();

        when(repository.retrieve(expected.getId().toString()))
                .thenReturn(Optional.of(fetchedEntity));

        var result = toDoService.retrieveById(expected.getId());

        assertEquals(expected, result);
        assertNotNull(fetchedEntity.getCreatedAt());
    }
}