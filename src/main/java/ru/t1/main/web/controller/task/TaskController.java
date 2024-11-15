package ru.t1.main.web.controller.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.main.aspect.annotation.Auditable;
import ru.t1.main.dto.task.TaskCreateUpdateDto;
import ru.t1.main.dto.task.TaskList;
import ru.t1.main.dto.task.TaskListResponseDto;
import ru.t1.main.dto.task.TaskResponseDto;
import ru.t1.main.mapper.TaskMapper;
import ru.t1.main.model.Task;
import ru.t1.main.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Auditable(methodName = "Получение идеи по идентификатору")
    @GetMapping(path = "/{taskId}")
    public TaskResponseDto getTaskById(@PathVariable Long taskId) {
        Task response = taskService.getTaskById(taskId);
        return taskMapper.taskToResponseDto(response);
    }

    @Auditable(methodName = "Получение списка идей")
    @GetMapping
    public TaskListResponseDto getAllTasks(@RequestParam(defaultValue = "0") int minId,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        TaskList response = taskService.getAllTasks(minId, pageSize);
        return taskMapper.taskListToResponseDto(response);
    }

    @Auditable(methodName = "Создание идеи")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TaskResponseDto createTask(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @RequestBody TaskCreateUpdateDto taskCreateDto) {
        Task request = taskMapper.taskCreateDtoToTask(taskCreateDto);
        request.setUserId(userId);
        Task response = taskService.createTask(request);
        return taskMapper.taskToResponseDto(response);
    }

    @Auditable(methodName = "Обновление идеи")
    @PutMapping(path = "/{taskId}")
    public TaskResponseDto updateTask(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable Long taskId,
                                      @RequestBody TaskCreateUpdateDto taskForUpdate) {
        Task updateRequest = taskMapper.taskCreateDtoToTask(taskForUpdate);
        Task response = taskService.updateTask(userId, taskId, updateRequest);
        return taskMapper.taskToResponseDto(response);
    }

    @Auditable(methodName = "Удаление идеи")
    @DeleteMapping(path = "/{taskId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable long taskId) {
        taskService.deleteTask(userId, taskId);
    }
}
