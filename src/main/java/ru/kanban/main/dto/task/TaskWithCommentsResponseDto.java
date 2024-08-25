package ru.kanban.main.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.comment.CommentListShortResponseDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.Status;

/**
 * The type Task with comments response dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskWithCommentsResponseDto {
    @Schema(description = "Идентификатор задачи")
    Long id;
    @Schema(description = "Название задачи")
    String name;
    @Schema(description = "Описание задачи")
    String description;
    @Schema(description = "Статус выполнения задачи")
    Status status;
    @Schema(description = "Автор задачи")
    UserResponseDto author;
    @Schema(description = "Исполнитель задачи")
    UserResponseDto executor;
    @Schema(description = "Комментарии к задаче")
    CommentListShortResponseDto comments;
}
