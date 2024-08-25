package ru.kanban.main.service;

import ru.kanban.main.model.Comment;

import java.util.List;

public interface CommentService {

    /**
     * Create comment comment.
     *
     * @param comment  the comment
     * @param taskId   the task id
     * @param authorId the author id
     * @return the comment
     */
    Comment createComment(Comment comment, long taskId, long authorId);

    /**
     * Gets comment by id.
     *
     * @param commentId the comment id
     * @return the comment by id
     */
    Comment getCommentById(long commentId);

    /**
     * Update comment by author comment.
     *
     * @param commentId        the comment id
     * @param authorId         the author id
     * @param commentForUpdate the comment for update
     * @return the comment
     */
    Comment updateCommentByAuthor(long commentId, long authorId, Comment commentForUpdate);

    /**
     * Delete comment.
     *
     * @param authorId  the author id
     * @param commentId the comment id
     */
    void deleteComment(long authorId, long commentId);

    /**
     * Gets all comments.
     *
     * @param taskId   the task id
     * @param minId    the min id
     * @param pageSize the page size
     * @return the all comments
     */
    List<Comment> getAllComments(long taskId, int minId, int pageSize);

    /**
     * Count comments long.
     *
     * @param taskId the task id
     * @return the long
     */
    long countComments(long taskId);
}
