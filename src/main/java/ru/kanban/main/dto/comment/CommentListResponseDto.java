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
 * The type Comment list response dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentListResponseDto {
    @Schema(description = "Список комментариев с отсылкой на задачи")
    List<CommentResponseDto> comments;
    @Schema(description = "Длина списка")
    Long totalComments;
}
