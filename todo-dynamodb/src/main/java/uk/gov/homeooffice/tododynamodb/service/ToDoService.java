package uk.gov.homeooffice.tododynamodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;
import uk.gov.homeooffice.tododynamodb.repository.ToDoRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class ToDoService {
    private final ToDoRepository repository;

    @Autowired
    public ToDoService(ToDoRepository repository) {
        this.repository = repository;
    }

    public ToDoDTO createTodo(CreateToDoDTO in) {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID().toString(),
                in.getTitle(),
                in.getDescription(),
                in.getAssignee(),
                Objects.isNull(in.getDue()) ? null : in.getDue().toString()
        );

        repository.save(toDoEntity);
        return ToDoDTO.fromToDoEntity(toDoEntity);
    }
}
