package uk.gov.homeooffice.tododynamodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.homeooffice.tododynamodb.model.dtos.CreateToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;
import uk.gov.homeooffice.tododynamodb.service.ToDoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("todo")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoDTO create(@RequestBody CreateToDoDTO in) {
        return toDoService.createToDo(in);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToDoDTO> get(@PathVariable UUID id) {
        try {
            var tooDo = toDoService.retrieveById(id);
            return ResponseEntity.ok(tooDo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get-all")
    public List<ToDoDTO> getAll() {
        return toDoService.getToDos();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody ToDoDTO updated) {
        try {
            toDoService.updateToDo(updated);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            toDoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
