package ru.kanban.main.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * The type Comment list short response dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentListShortResponseDto {
    @Schema(description = "Список комментариев без отсылки на задачи")
    List<CommentShortResponseDto> comments;
    @Schema(description = "Длина списка")
    int totalComments;
}
