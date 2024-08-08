package ru.kanban.main.dto.task;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreateUpdateDto {
    @NotBlank(groups = {New.class}, message = "Название задачи не должно быть пустым")
    String name;
    String description;
    Status status;
}
