package ru.kanban.main.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * The type Task list response dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskListResponseDto {
    @Schema(description = "Список задач с комментариями")
    List<TaskWithCommentsResponseDto> tasks;
    @Schema(description = "Длина списка")
    Long totalTasks;
}