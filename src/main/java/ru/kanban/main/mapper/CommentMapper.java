package ru.kanban.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.kanban.main.dto.comment.CommentCreateUpdateDto;
import ru.kanban.main.dto.comment.CommentListResponseDto;
import ru.kanban.main.dto.comment.CommentListShortResponseDto;
import ru.kanban.main.dto.comment.CommentResponseDto;
import ru.kanban.main.dto.comment.CommentShortResponseDto;
import ru.kanban.main.model.Comment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The interface Comment mapper.
 */
@Mapper(uses = {TaskMapper.class, UserMapper.class})
@Component
public interface CommentMapper {

    /**
     * Comment dto to comment comment.
     *
     * @param commentCreateUpdateDto the comment create update dto
     * @return the comment
     */
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "date", ignore = true)
    Comment commentDtoToComment(CommentCreateUpdateDto commentCreateUpdateDto);

    /**
     * Comment to comment response dto comment response dto.
     *
     * @param comment the comment
     * @return the comment response dto
     */
    CommentResponseDto commentToCommentResponseDto(Comment comment);

    /**
     * Comment to comment short response dto comment short response dto.
     *
     * @param comment the comment
     * @return the comment short response dto
     */
    CommentShortResponseDto commentToCommentShortResponseDto(Comment comment);

    /**
     * To comments list response dto.
     *
     * @param comments the comments
     * @param count    the count
     * @return the comment list response dto
     */
    default CommentListResponseDto toCommentsListResponseDto(List<CommentResponseDto> comments, long count) {
        return CommentListResponseDto.builder().comments(comments).totalComments(count).build();
    }

    /**
     * To comments list short response dto.
     *
     * @param comments the comments
     * @return the comment list short response dto
     */
    default CommentListShortResponseDto toCommentsListShortResponseDto(List<Comment> comments) {
        if (comments == null) {
            return new CommentListShortResponseDto();
        }
        List<CommentShortResponseDto> shortCommentsList = comments.stream()
                .map(this::commentToCommentShortResponseDto)
                .sorted(Comparator.comparing(CommentShortResponseDto::getDate))
                .limit(10)
                .collect(Collectors.toList());
        return CommentListShortResponseDto.builder().comments(shortCommentsList).totalComments(comments.size()).build();
    }
}
