package uk.gov.homeooffice.todo.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import uk.gov.homeooffice.todo.model.entities.ToDoEntity;

@Configuration
public class DynamoDbInitialiser {

    @Bean
    public CommandLineRunner createTableIfNotExists(
            DynamoDbClient client,
            DynamoDbEnhancedClient enhancedClient) {

        return args -> {
            String tableName = "ToDo";

            try {
                client.describeTable(r -> r.tableName(tableName));
                System.out.println("Table already exists");
            } catch (ResourceNotFoundException e) {
                System.out.println("Creating DynamoDB table: " + tableName);

                DynamoDbTable<ToDoEntity> table =
                        enhancedClient.table(
                                tableName,
                                TableSchema.fromBean(ToDoEntity.class)
                        );

                table.createTable();
            }
        };
    }
}

