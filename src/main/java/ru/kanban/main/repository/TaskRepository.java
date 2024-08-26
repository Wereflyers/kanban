package ru.kanban.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.kanban.main.model.Task;

import java.util.List;

/**
 * The interface Task repository.
 */
public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task> {
    /**
     * Find all by author id list.
     *
     * @param authorId    the author id
     * @param pageRequest the page request
     * @return the list
     */
    List<Task> findAllByAuthorId(long authorId, Pageable pageRequest);

    /**
     * Find all by executor id list.
     *
     * @param executorId  the executor id
     * @param pageRequest the page request
     * @return the list
     */
    List<Task> findAllByExecutorId(long executorId, Pageable pageRequest);
}
