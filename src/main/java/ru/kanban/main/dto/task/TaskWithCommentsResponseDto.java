package ru.kanban.main.dto.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.comment.CommentListShortResponseDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskWithCommentsResponseDto {
    Long id;
    String name;
    String description;
    Status status;
    UserResponseDto author;
    UserResponseDto executor;
    CommentListShortResponseDto comments;
}
