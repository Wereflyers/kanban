package ru.kanban.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.configuration.PageRequestOverride;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.Comment;
import ru.kanban.main.model.Task;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskService taskService;

    public Comment createComment(Comment comment, long taskId, long authorId) {
        User author = userService.getUser(authorId);
        Task task = taskService.getTaskById(taskId);

        comment.setAuthor(author);
        comment.setTask(task);
        comment.setDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("Comment " + commentId + " not found"));
    }

    public Comment updateCommentByAuthor(long commentId, long authorId, Comment commentForUpdate) {
        Comment comment = getCommentById(commentId);
        checkBuyerAccessRightsToUpdateComment(commentId, authorId);
        if (commentForUpdate.getText() != null && !commentForUpdate.getText().isBlank()) {
            comment.setText(commentForUpdate.getText());
        }
        return commentRepository.save(comment);
    }

    public void deleteComment(long authorId, long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment " + commentId + " not found");
        }
        checkBuyerAccessRightsToUpdateComment(commentId, authorId);
        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments(long taskId, int minId, int pageSize) {
        Sort sort = Sort.by("date").descending();
        PageRequest page = PageRequestOverride.of(minId, pageSize, sort);
        return commentRepository.findAllByTaskId(taskId, page);
    }

    @Transactional(readOnly = true)
    public long countComments(long taskId) {
        return commentRepository.countAllByTaskId(taskId);
    }

    private void checkBuyerAccessRightsToUpdateComment(long commentId, long authorId) {
        Comment comment = getCommentById(commentId);
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new AccessDenialException();
        }
    }
}
