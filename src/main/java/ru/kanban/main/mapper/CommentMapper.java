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

@Mapper(uses = {TaskMapper.class, UserMapper.class})
@Component
public interface CommentMapper {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "date", ignore = true)
    Comment commentDtoToComment(CommentCreateUpdateDto commentCreateUpdateDto);

    CommentResponseDto commentToCommentResponseDto(Comment comment);

    CommentShortResponseDto commentToCommentShortResponseDto(Comment comment);

    default CommentListResponseDto toCommentsListResponseDto(List<CommentResponseDto> comments, long count) {
        return CommentListResponseDto.builder().comments(comments).totalComments(count).build();
    }

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
