package ru.kanban.main.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.validation.New;
import ru.kanban.main.model.Status;

import javax.validation.constraints.NotBlank;

/**
 * The type Task create update dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateUpdateDto {
    @Schema(description = "Название задачи")
    @NotBlank(groups = {New.class}, message = "Название задачи не должно быть пустым")
    String name;
    @Schema(description = "Описание задачи")
    String description;
    @Schema(description = "Статус выполнения задачи")
    Status status;
}
