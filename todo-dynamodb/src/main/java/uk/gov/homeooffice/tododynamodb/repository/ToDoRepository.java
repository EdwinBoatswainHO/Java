package uk.gov.homeooffice.tododynamodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.GetAttr;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ToDoRepository {


    private final DynamoDbTable<ToDoEntity> todos;

    public ToDoRepository(DynamoDbTable<ToDoEntity> todos) {
        this.todos = todos;
    }



    public void save(ToDoEntity todo) {
        todos.putItem(todo);
    }

    public Optional<ToDoEntity> retrieve(final String id) {
        return Optional.ofNullable(todos.getItem(request -> request.key(key->key.partitionValue(id))));
    }

    public boolean delete(String id) {
        var previousTodo = todos.deleteItem(Key.builder().partitionValue(id).build());
        return Objects.nonNull(previousTodo);
    }

    public List<ToDoDTO> retrieveAll() {
        // Expensive in real life. Don't use Dynamo if you need table scans.
        return todos.scan().items().stream()
                .map(ToDoDTO::fromToDoEntity)
                .toList();
    }

    // For testing
    public void clear() {
        var todoList = todos.scan().items().stream().toList();
        todoList.forEach(todos::deleteItem);
    }


}
