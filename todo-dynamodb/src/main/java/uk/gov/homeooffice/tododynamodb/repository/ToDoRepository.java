package uk.gov.homeooffice.tododynamodb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.GetAttr;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

@Repository
public class ToDoRepository {


    private final DynamoDbTable<ToDoEntity> todos;

    public ToDoRepository(DynamoDbTable<ToDoEntity> todos) {
        this.todos = todos;
    }

//    public clear() {
//        todo.delete
//    }

    public void save(ToDoEntity todo) {
        todos.putItem(todo);
    }

    public ToDoEntity retrieve(final String id) {
        return todos.getItem(request -> request.key(key->key.partitionValue(id)));
    }
}
