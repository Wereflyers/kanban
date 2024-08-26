package ru.kanban.main.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.model.Task;

import java.util.List;

/**
 * The type Task list response dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskList {
    @Schema(description = "Список задач с комментариями")
    List<Task> tasks;
    @Schema(description = "Длина списка")
    Long totalTasks;
}