package ru.kanban.main.web.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kanban.main.dto.comment.CommentListResponseDto;
import ru.kanban.main.dto.comment.CommentResponseDto;
import ru.kanban.main.mapper.CommentMapper;
import ru.kanban.main.model.Comment;
import ru.kanban.main.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Public comment controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class PublicCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    /**
     * Gets comment by id.
     *
     * @param commentId the comment id
     * @return the comment by id
     */
    @GetMapping(path = "/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable Long commentId) {
        Comment response = commentService.getCommentById(commentId);
        return commentMapper.commentToCommentResponseDto(response);
    }

    /**
     * Gets all comments.
     *
     * @param taskId   the task id
     * @param minId    the min id
     * @param pageSize the page size
     * @return the all comments
     */
    @GetMapping(path = "/task/{taskId}")
    public CommentListResponseDto getAllComments(@PathVariable long taskId,
                                                 @RequestParam(defaultValue = "0") int minId,
                                                 @RequestParam(defaultValue = "10") int pageSize) {
        List<Comment> commentList = commentService.getAllComments(taskId, minId, pageSize);
        List<CommentResponseDto> response = commentList.stream()
                .map(commentMapper::commentToCommentResponseDto)
                .collect(Collectors.toList());
        long totalCount = commentService.countComments(taskId);
        return commentMapper.toCommentsListResponseDto(response, totalCount);
    }
}
