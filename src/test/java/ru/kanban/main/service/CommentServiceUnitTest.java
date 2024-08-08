package ru.kanban.main.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.Comment;
import ru.kanban.main.model.Task;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.CommentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceUnitTest {
    @Mock
    private UserService userService;
    @Mock
    private TaskService taskService;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    private static User user;
    private static Task task;

    @BeforeEach
    void init() {
        user = User.builder().id(1L).build();
        task = Task.builder().id(1L).build();
    }

    @Test
    void createComment_shouldReturnComment() {
        // given
        Comment request = Comment.builder().text("comment").build();

        // when
        when(userService.getUser(1L)).thenReturn(user);
        when(taskService.getTaskById(1L)).thenReturn(task);
        when(commentRepository.save(any())).thenReturn(request);

        // then
        Comment response = commentService.createComment(request, task.getId(), user.getId());
        assertEquals(request.getText(), response.getText());
        assertEquals(request.getAuthor().getId(), response.getAuthor().getId());
        assertEquals(request.getTask().getId(), response.getTask().getId());
    }

    @Test
    void getCommentById_shouldThrowException_whenIdInvalid() {
        // when
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> commentService.getCommentById(123L));
    }

    @Test
    void updateCommentByAuthor_shouldUpdateCorrectly_whenValidUser() {
        // given
        Comment oldComment = Comment.builder().id(1L).author(user).task(task).text("old comment").build();
        Comment updateRequest = Comment.builder().text("comment").build();
        oldComment.setText(updateRequest.getText());

        // when
        when(commentRepository.findById(oldComment.getId())).thenReturn(Optional.of(oldComment));
        when(commentRepository.save(any())).thenReturn(oldComment);

        // then
        Comment response = commentService.updateCommentByAuthor(oldComment.getId(), user.getId(), updateRequest);
        assertEquals(oldComment.getAuthor(), response.getAuthor());
        assertEquals(oldComment.getText(), response.getText());
    }

    @Test
    void updateCommentByAuthor_shouldThrowException_whenUserIsNotAuthor() {
        // given
        long userId = 2L;
        Comment oldComment = Comment.builder().id(1L).author(user).task(task).text("old comment").build();
        Comment updateRequest = Comment.builder().text("comment").build();

        // when
        when(commentRepository.findById(oldComment.getId())).thenReturn(Optional.of(oldComment));

        // then
        assertThrows(AccessDenialException.class,
                () -> commentService.updateCommentByAuthor(oldComment.getId(), userId, updateRequest));
    }

    @Test
    void deleteCommentByAuthor_shouldThrowException_whenCommentNotFound() {
        // given
        long userId = 2L;
        Comment comment = Comment.builder().id(1L).author(user).text("old comment").build();

        // when
        when(commentRepository.existsById(comment.getId())).thenReturn(false);

        // then
        assertThrows(EntityNotFoundException.class,
                () -> commentService.deleteComment(userId, comment.getId()));
    }
}
