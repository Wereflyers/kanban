package ru.kanban.main.service.impl;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.configuration.PageRequestOverride;
import ru.kanban.main.dto.task.TaskList;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.QTask;
import ru.kanban.main.model.Status;
import ru.kanban.main.model.Task;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.TaskRepository;
import ru.kanban.main.service.TaskService;
import ru.kanban.main.service.UserService;
import ru.kanban.main.util.QPredicates;

import java.util.List;

/**
 * The type Task service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

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
    public TaskList getAllTasks(int minId, int pageSize, Task searchCriteria) {
        PageRequest page = PageRequestOverride.of(minId, pageSize);

        QTask task = QTask.task;
        Predicate predicate;
        Page<Task> tasks;
        long count;

        if (searchCriteria != null) {
            predicate = QPredicates.builder()
                    .add(searchCriteria.getName(), task.name::contains)
                    .add(searchCriteria.getDescription(), task.description::contains)
                    .add(searchCriteria.getStatus(), task.status::eq)
                    .buildAnd();

            tasks = taskRepository.findAll(predicate, page);
            count = taskRepository.count(predicate);
        } else {
            tasks = taskRepository.findAll(page);
            count = taskRepository.count();
        }

        return TaskList.builder()
                .tasks(tasks.getContent())
                .totalTasks(count)
                .build();
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
}
