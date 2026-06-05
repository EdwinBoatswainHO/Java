package uk.gov.homeooffice.tododynamodb.model.entities;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoEntity {
    @Getter(onMethod_={@DynamoDbPartitionKey})
    private String id;
    private String title;
    private String description;
    private String assignee;
    private String due;
}
