package ru.kanban.main.web.controller.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kanban.main.dto.task.TaskCreateUpdateDto;
import ru.kanban.main.dto.task.TaskResponseDto;
import ru.kanban.main.dto.validation.New;
import ru.kanban.main.mapper.TaskMapper;
import ru.kanban.main.model.Task;
import ru.kanban.main.service.TaskService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/task")
public class UserTaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskResponseDto createTask(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @RequestBody @Validated(New.class) TaskCreateUpdateDto taskCreateUpdateDto) {
        Task request = taskMapper.taskCreateDtoToTask(taskCreateUpdateDto);
        Task response = taskService.createTask(request, userId);
        return taskMapper.taskToResponseDto(response);
    }

    @PatchMapping(path = "/{taskId}")
    @PreAuthorize("hasAuthority('user:write')")
    public TaskResponseDto updateTask(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Long taskId,
                                            @RequestBody @Valid TaskCreateUpdateDto taskForUpdate) {
        Task updateRequest = taskMapper.taskCreateDtoToTask(taskForUpdate);
        Task response = taskService.updateTaskByAuthor(taskId, userId, updateRequest);
        return taskMapper.taskToResponseDto(response);
    }

    @PatchMapping(path = "/{taskId}/executor/{executorId}")
    @PreAuthorize("hasAuthority('user:write')")
    public TaskResponseDto updateExecutorOfTask(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long taskId,
                                                @PathVariable long executorId) {
        Task response = taskService.updateExecutorOfTask(taskId, userId, executorId);
        return taskMapper.taskToResponseDto(response);
    }

    @DeleteMapping(path = "/{taskId}")
    @PreAuthorize("hasAuthority('user:write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long taskId) {
        taskService.deleteTask(taskId, userId);
    }
}
