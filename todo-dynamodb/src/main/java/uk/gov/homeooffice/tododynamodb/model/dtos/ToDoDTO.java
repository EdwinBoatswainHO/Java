package uk.gov.homeooffice.tododynamodb.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class ToDoDTO {
    private UUID id;
    private String title;
    private String description;
    private String assignee;
    private LocalDateTime due;

    public static ToDoDTO fromToDoEntity(ToDoEntity entity) {
        var due = entity.getDue();
        LocalDateTime dueDt = null;

        if (Objects.nonNull(due)) {
            try {
                dueDt = LocalDateTime.parse(due);
            } catch (DateTimeParseException e) {
                // unlikely but...
                System.err.printf("Due date from ToDo entity %s : %s failed to parse.\n",
                        entity.getId(), entity.getTitle());
            }
        }

        return ToDoDTO.builder()
                .id(UUID.fromString(entity.getId()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .assignee(entity.getAssignee())
                .due(dueDt)
                .build();
    }

}
