package ru.kanban.main.web.controller.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kanban.main.dto.task.TaskCreateUpdateDto;
import ru.kanban.main.dto.task.TaskList;
import ru.kanban.main.dto.task.TaskListResponseDto;
import ru.kanban.main.dto.task.TaskResponseDto;
import ru.kanban.main.dto.task.TaskWithCommentsResponseDto;
import ru.kanban.main.mapper.TaskMapper;
import ru.kanban.main.model.Task;
import ru.kanban.main.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Public task controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/task")
public class PublicTaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    /**
     * Gets task by id.
     *
     * @param taskId the task id
     * @return the task by id
     */
    @GetMapping(path = "/{taskId}")
    public TaskResponseDto getTaskById(@PathVariable Long taskId) {
        Task response = taskService.getTaskById(taskId);
        return taskMapper.taskToResponseDto(response);
    }

    /**
     * Gets all tasks.
     *
     * @param minId    the min id
     * @param pageSize the page size
     * @return the all tasks
     */
    @PostMapping(value = "/search")
    public TaskListResponseDto getAllTasks(@RequestParam(defaultValue = "0") int minId,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestBody(required = false) TaskCreateUpdateDto searchCriteria) {
        TaskList response = taskService.getAllTasks(minId, pageSize,
                        taskMapper.taskCreateDtoToTask(searchCriteria));
        return taskMapper.taskListToResponseDto(response);
    }

    /**
     * Gets all tasks by author with comments.
     *
     * @param minId    the min id
     * @param pageSize the page size
     * @param authorId the author id
     * @return the all tasks by author with comments
     */
    @GetMapping("/author/{authorId}")
    public TaskListResponseDto getAllTasksByAuthorWithComments(@RequestParam(required = false, defaultValue = "0") int minId,
                                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                               @PathVariable long authorId) {
        List<TaskWithCommentsResponseDto> response = taskService.getAllTasksByAuthor(authorId, minId, pageSize).stream()
                .map(taskMapper::taskToTaskWithCommentsResponseDto)
                .collect(Collectors.toList());
        return taskMapper.toTasksListResponseDto(response, response.size());
    }

    /**
     * Gets all tasks by executor with comments.
     *
     * @param minId      the min id
     * @param pageSize   the page size
     * @param executorId the executor id
     * @return the all tasks by executor with comments
     */
    @GetMapping("/executor/{executorId}")
    public TaskListResponseDto getAllTasksByExecutorWithComments(@RequestParam(name = "minId", defaultValue = "0") int minId,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                               @PathVariable long executorId) {
        List<TaskWithCommentsResponseDto> response = taskService.getAllTasksByExecutor(executorId, minId, pageSize).stream()
                .map(taskMapper::taskToTaskWithCommentsResponseDto)
                .collect(Collectors.toList());
        return taskMapper.toTasksListResponseDto(response, response.size());
    }
}
