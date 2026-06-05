package uk.gov.homeooffice.tododynamodb.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ToDoRepositoryTest {

    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoRepositoryTest(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

//    @BeforeEach
//    @AfterEach
//    void clearDB() {
//        toDoRepository.clear();
//    }

    @Test
    void repositoryIsAutowired() {
        assertNotNull(toDoRepository);
    }

    @Test
    void canSaveAndRetrieveAToDo() {
        ToDoEntity todo = new ToDoEntity(UUID.randomUUID().toString(), "title", "desc", "assignee", LocalDate.now().toString());

        toDoRepository.save(todo);
        var retrieved = toDoRepository.retrieve(todo.getId());

        assertEquals(todo, retrieved);
    }
}