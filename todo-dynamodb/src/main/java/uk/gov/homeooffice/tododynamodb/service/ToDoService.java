package uk.gov.homeooffice.tododynamodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;
import uk.gov.homeooffice.tododynamodb.repository.ToDoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ToDoService {
    private final ToDoRepository repository;

    @Autowired
    public ToDoService(ToDoRepository repository) {
        this.repository = repository;
    }

    public ToDoDTO createToDo(CreateToDoDTO in) {
        ToDoEntity toDoEntity = new ToDoEntity(
                UUID.randomUUID().toString(),
                in.getTitle(),
                in.getDescription(),
                in.getAssignee(),
                Objects.isNull(in.getDue()) ? null : in.getDue().toString(),
                LocalDateTime.now().toString()
        );

        repository.save(toDoEntity);
        return ToDoDTO.fromToDoEntity(toDoEntity);
    }

    public ToDoDTO retrieveById(UUID id)
            throws IllegalArgumentException {
        var retrieved = repository.retrieve(id.toString());
        return retrieved.map(ToDoDTO::fromToDoEntity)
                .orElseThrow(
                        () -> new IllegalArgumentException("Task with ID " + id + " does not exist.")
                );

    }

    // Order is arbitrary, downside of not using a numeric ID.
    // Perhaps introduce creation date / time
    public List<ToDoDTO> getToDos() {
        return repository.retrieveAll();
    }

    public void updateToDo(ToDoDTO toDo)
            throws IllegalArgumentException {
        var existing = repository.retrieve(toDo.getId().toString());
        if (existing.isEmpty())
            throw new IllegalArgumentException("Todo with ID " + toDo.getId().toString() + " does not exist");

        /*
         Lazy approach replaces the whole item rather than updates with supplied fields
         Onus is on the client to ensure they send the whole object.
        */
        repository.save(new ToDoEntity(
                toDo.getId().toString(),
                toDo.getTitle(),
                toDo.getDescription(),
                toDo.getAssignee(),
                toDo.getDue().toString(),
                toDo.getCreatedAt().toString()));
    }

    public void deleteById(UUID id)
            throws IllegalArgumentException {
        var result = repository.delete(id.toString());

        if (!result)
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
    }
}
