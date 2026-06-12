package uk.gov.homeooffice.tododynamodb.model.dtos;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.annotations.NotNull;
import uk.gov.homeooffice.tododynamodb.model.entities.ToDoEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class CreateToDoDTO {
    @NotNull
    private String title;
    private String description;
    private String assignee;
    @Nullable
    private LocalDateTime due;
}
