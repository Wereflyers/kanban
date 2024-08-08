package ru.kanban.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.configuration.PageRequestOverride;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.Status;
import ru.kanban.main.model.Task;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.TaskRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task createTask(Task task, long authorId) {
        User author = userService.getUser(authorId);
        task.setAuthor(author);
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

    public Task updateTaskByAuthor(long taskId, long authorId, Task taskForUpdate) {
        Task task = getTaskById(taskId);
        checkAuthorAccessRightsToUpdateTask(taskId, authorId);
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

    public Task updateExecutorOfTask(long taskId, long authorId, long executorId) {
        Task task = getTaskById(taskId);
        checkAuthorAccessRightsToUpdateTask(taskId, authorId);
        User executor = userService.getUser(executorId);
        task.setExecutor(executor);
        return taskRepository.save(task);
    }

    public void deleteTask(long taskId, long authorId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task " + taskId + " not found");
        }
        checkAuthorAccessRightsToUpdateTask(taskId, authorId);
        taskRepository.deleteById(taskId);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks(int minId, int pageSize) {
        PageRequest page = PageRequestOverride.of(minId, pageSize);
        return taskRepository.findAll(page).toList();
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksByAuthor(long authorId, int minId, int pageSize) {
        PageRequest page = PageRequestOverride.of(minId, pageSize);
        return taskRepository.findAllByAuthorId(authorId, page);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasksByExecutor(long executorId, int minId, int pageSize) {
        PageRequest page = PageRequestOverride.of(minId, pageSize);
        return taskRepository.findAllByExecutorId(executorId, page);
    }

    @Transactional(readOnly = true)
    public void checkAuthorAccessRightsToUpdateTask(long taskId, long authorId) {
        Task task = getTaskById(taskId);
        if (!task.getAuthor().getId().equals(authorId)) {
            throw new AccessDenialException();
        }
    }

    @Transactional(readOnly = true)
    public long countTasks() {
        return taskRepository.count();
    }
}
