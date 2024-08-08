package ru.kanban.main.dto.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponseDto {
    Long id;
    String name;
    String description;
    Status status;
    UserResponseDto author;
    UserResponseDto executor;
}
