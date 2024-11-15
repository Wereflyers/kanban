package ru.t1.main.service;

import ru.t1.main.dto.task.TaskList;
import ru.t1.main.model.Task;

public interface TaskService {

    Task createTask(Task task);

    Task getTaskById(long taskId);

    Task updateTask(long userId, long taskId, Task taskForUpdate);

    void deleteTask(long userId, long taskId);

    TaskList getAllTasks(int minId, int pageSize);
}
