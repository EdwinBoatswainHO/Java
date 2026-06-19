package uk.gov.homeooffice.tododynamodb.model.entities;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import uk.gov.homeooffice.tododynamodb.model.dtos.ToDoDTO;

import java.util.Objects;

@DynamoDbBean
@Data
@NoArgsConstructor
public class ToDoEntity {
    private String id;
    private String title;
    private String description;
    private String assignee;
    private String due;
    @EqualsAndHashCode.Exclude
    private String createdAt;

    @Setter(AccessLevel.NONE)
    private Long version;


    @DynamoDbVersionAttribute
    public Long getVersion() {
        return version;
    }


    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @Builder
    public ToDoEntity(String id, String title, String description, String assignee, String due, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.due = due;
        this.createdAt = createdAt;
    }

    public static ToDoEntity from(ToDoDTO dto) {
        return ToDoEntity.builder()
                .id(dto.getId().toString())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .assignee(dto.getAssignee())
                .due(Objects.isNull(dto.getDue()) ? null : dto.getDue().toString())
                .build();
    }
}
