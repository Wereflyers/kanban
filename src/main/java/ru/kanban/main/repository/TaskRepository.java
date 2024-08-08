package ru.kanban.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kanban.main.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAuthorId(long authorId, Pageable pageRequest);

    List<Task> findAllByExecutorId(long executorId, Pageable pageRequest);
}
