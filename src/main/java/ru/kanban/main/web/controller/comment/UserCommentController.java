package ru.kanban.main.web.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kanban.main.dto.comment.CommentCreateUpdateDto;
import ru.kanban.main.dto.comment.CommentResponseDto;
import ru.kanban.main.dto.validation.New;
import ru.kanban.main.mapper.CommentMapper;
import ru.kanban.main.model.Comment;
import ru.kanban.main.service.CommentService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class UserCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping(path = "/{taskId}/create")
    @PreAuthorize("hasAuthority('user:write')")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                            @PathVariable Long taskId,
                                            @RequestBody @Validated(New.class) CommentCreateUpdateDto commentDto) {
        Comment request = commentMapper.commentDtoToComment(commentDto);
        Comment response = commentService.createComment(request, taskId, userId);
        return commentMapper.commentToCommentResponseDto(response);
    }

    @PatchMapping(path = "/{commentId}/update")
    @PreAuthorize("hasAuthority('user:write')")
    public CommentResponseDto updateCommentByAuthor(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody @Valid CommentCreateUpdateDto updateDto) {
        Comment request = commentMapper.commentDtoToComment(updateDto);
        Comment response = commentService.updateCommentByAuthor(commentId, userId, request);
        return commentMapper.commentToCommentResponseDto(response);
    }

    @DeleteMapping(path = "/{commentId}")
    @PreAuthorize("hasAuthority('user:write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCommentByAuthor(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId);
    }
}
