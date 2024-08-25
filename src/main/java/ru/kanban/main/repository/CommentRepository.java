package ru.kanban.main.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kanban.main.model.Comment;

import java.util.List;

/**
 * The interface Comment repository.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Find all by task id list.
     *
     * @param taskId   the task id
     * @param pageable the pageable
     * @return the list
     */
    List<Comment> findAllByTaskId(long taskId, Pageable pageable);

    /**
     * Count all by task id long.
     *
     * @param taskId the task id
     * @return the long
     */
    long countAllByTaskId(long taskId);
}
