package uk.gov.homeooffice.tododynamodb.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.BillingMode;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;
import software.amazon.awssdk.services.dynamodb.model.*;

@Configuration
public class DynamoDbInitialiser {
    @Value("${app.dynamodb.todoTable}")
    private String todoTableName;


    @Bean
    public CommandLineRunner createTableIfNotExists(
            DynamoDbClient client,
            DynamoDbEnhancedClient enhancedClient) {

        return args -> {
            String tableName = todoTableName;

            try {
                client.describeTable(r -> r.tableName(tableName));
                System.out.println("Table already exists");

            } catch (ResourceNotFoundException e) {
                System.out.println("Creating DynamoDB table: " + tableName);

                client.createTable(r -> r
                        .tableName(tableName)
                        .billingMode(BillingMode.PAY_PER_REQUEST)
                        .attributeDefinitions(
                                java.util.List.of(
                                        AttributeDefinition.builder()
                                                .attributeName("id")
                                                .attributeType(ScalarAttributeType.S)
                                                .build()
                                )
                        )
                        .keySchema(
                                java.util.List.of(
                                        KeySchemaElement.builder()
                                                .attributeName("id")
                                                .keyType(KeyType.HASH)
                                                .build()
                                )
                        )
                );
            }
        };
    }

}

