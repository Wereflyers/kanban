package ru.t1.main.mapper;

import org.mapstruct.Mapper;
import ru.t1.main.dto.task.TaskCreateUpdateDto;
import ru.t1.main.dto.task.TaskList;
import ru.t1.main.dto.task.TaskListResponseDto;
import ru.t1.main.dto.task.TaskResponseDto;
import ru.t1.main.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task taskCreateDtoToTask(TaskCreateUpdateDto taskCreateUpdateDto);

    TaskResponseDto taskToResponseDto(Task task);

    TaskListResponseDto taskListToResponseDto(TaskList taskList);
}
