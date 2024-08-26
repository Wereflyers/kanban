package ru.kanban.main.service;

import ru.kanban.main.dto.task.TaskList;
import ru.kanban.main.model.Task;

import java.util.List;

public interface TaskService {

    /**
     * Create task task.
     *
     * @param task     the task
     * @param authorId the author id
     * @return the task
     */
    Task createTask(Task task, long authorId);

    /**
     * Gets task by id.
     *
     * @param taskId the task id
     * @return the task by id
     */
    Task getTaskById(long taskId);

    /**
     * Update task by author task.
     *
     * @param taskId        the task id
     * @param authorId      the author id
     * @param taskForUpdate the task for update
     * @return the task
     */
    Task updateTaskByAuthor(long taskId, long authorId, Task taskForUpdate);

    /**
     * Update executor of task task.
     *
     * @param taskId     the task id
     * @param authorId   the author id
     * @param executorId the executor id
     * @return the task
     */
    Task updateExecutorOfTask(long taskId, long authorId, long executorId);

    /**
     * Delete task.
     *
     * @param taskId   the task id
     * @param authorId the author id
     */
    void deleteTask(long taskId, long authorId);

    /**
     * Gets all tasks.
     *
     * @param minId    the min id
     * @param pageSize the page size
     * @return the all tasks
     */
    TaskList getAllTasks(int minId, int pageSize, Task searchCriteria);

    /**
     * Gets all tasks by author.
     *
     * @param authorId the author id
     * @param minId    the min id
     * @param pageSize the page size
     * @return the all tasks by author
     */
    List<Task> getAllTasksByAuthor(long authorId, int minId, int pageSize);

    /**
     * Gets all tasks by executor.
     *
     * @param executorId the executor id
     * @param minId      the min id
     * @param pageSize   the page size
     * @return the all tasks by executor
     */
    List<Task> getAllTasksByExecutor(long executorId, int minId, int pageSize);

    /**
     * Check author access rights to update task.
     *
     * @param taskId   the task id
     * @param authorId the author id
     */
    void checkAuthorAccessRightsToUpdateTask(long taskId, long authorId);
}
