package ru.kanban.main.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kanban.main.dto.task.TaskCreateUpdateDto;
import ru.kanban.main.dto.task.TaskListResponseDto;
import ru.kanban.main.dto.task.TaskResponseDto;
import ru.kanban.main.dto.task.TaskWithCommentsResponseDto;
import ru.kanban.main.model.Task;

import java.util.List;

@Mapper(uses = {UserMapper.class, CommentMapper.class})
@Component
public interface TaskMapper {

    Task taskCreateDtoToTask(TaskCreateUpdateDto taskCreateUpdateDto);

    TaskResponseDto taskToResponseDto(Task task);

    TaskWithCommentsResponseDto taskToTaskWithCommentsResponseDto(Task task);

    default TaskListResponseDto toTasksListResponseDto(List<TaskWithCommentsResponseDto> tasks, long count) {
        return TaskListResponseDto.builder().tasks(tasks).totalTasks(count).build();
    }
}
