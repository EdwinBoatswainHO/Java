package uk.gov.homeooffice.tododynamodb.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class CreateToDoDTO {
    private String title;
    private String description;
    private String assignee;
    private LocalDateTime due;
}
