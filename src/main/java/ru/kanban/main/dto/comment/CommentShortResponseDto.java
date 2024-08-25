package ru.kanban.main.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.user.UserResponseDto;

import java.time.LocalDateTime;

/**
 * The type Comment short response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentShortResponseDto {
    @Schema(description = "Автор комментария")
    UserResponseDto author;
    @Schema(description = "Текст комментария")
    String text;
    @Schema(description = "Дата написания комментария")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;
}
