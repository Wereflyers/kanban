package ru.kanban.main.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kanban.main.dto.task.TaskCreateUpdateDto;
import ru.kanban.main.dto.task.TaskListResponseDto;
import ru.kanban.main.dto.task.TaskResponseDto;
import ru.kanban.main.dto.task.TaskWithCommentsResponseDto;
import ru.kanban.main.model.Task;

import java.util.List;

/**
 * The interface Task mapper.
 */
@Mapper(uses = {UserMapper.class, CommentMapper.class})
@Component
public interface TaskMapper {

    /**
     * Task create dto to task task.
     *
     * @param taskCreateUpdateDto the task create update dto
     * @return the task
     */
    Task taskCreateDtoToTask(TaskCreateUpdateDto taskCreateUpdateDto);

    /**
     * Task to response dto task response dto.
     *
     * @param task the task
     * @return the task response dto
     */
    TaskResponseDto taskToResponseDto(Task task);

    /**
     * Task to task with comments response dto task with comments response dto.
     *
     * @param task the task
     * @return the task with comments response dto
     */
    TaskWithCommentsResponseDto taskToTaskWithCommentsResponseDto(Task task);

    /**
     * To tasks list response dto task list response dto.
     *
     * @param tasks the tasks
     * @param count the count
     * @return the task list response dto
     */
    default TaskListResponseDto toTasksListResponseDto(List<TaskWithCommentsResponseDto> tasks, long count) {
        return TaskListResponseDto.builder().tasks(tasks).totalTasks(count).build();
    }
}
