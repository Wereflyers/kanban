package ru.t1.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.main.dto.task.TaskList;
import ru.t1.main.exception.EntityNotFoundException;
import ru.t1.main.exception.NoRightsException;
import ru.t1.main.model.Status;
import ru.t1.main.model.Task;
import ru.t1.main.repository.TaskRepository;
import ru.t1.main.service.TaskService;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW);
        }
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task " + taskId + " not found"));
    }

    public Task updateTask(long userId, long taskId, Task taskForUpdate) {
        Task task = getTaskById(taskId);
        if (task.getUserId() != userId) {
            throw new NoRightsException();
        }
        if (taskForUpdate.getName() != null && !taskForUpdate.getName().isBlank()) {
            task.setName(taskForUpdate.getName());
        }
        if (taskForUpdate.getDescription() != null && !taskForUpdate.getDescription().isBlank()) {
            task.setDescription(taskForUpdate.getDescription());
        }
        if (taskForUpdate.getStatus() != null) {
            task.setStatus(taskForUpdate.getStatus());
        }
        return taskRepository.save(task);
    }

    public void deleteTask(long userId, long taskId) {
        Task task = getTaskById(taskId);
        if (task == null) {
            throw new EntityNotFoundException("Task " + taskId + " not found");
        }
        if (task.getUserId() != userId) {
            throw new NoRightsException();
        }
        taskRepository.deleteById(taskId);
    }

    @Transactional(readOnly = true)
    public TaskList getAllTasks(int minId, int pageSize) {
        PageRequest page = PageRequest.of(minId, pageSize);
        Page<Task> tasks = taskRepository.findAll(page);
        long count = taskRepository.count();

        return TaskList.builder()
                .tasks(tasks.getContent())
                .totalTasks(count)
                .build();
    }
}
