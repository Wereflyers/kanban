package ru.kanban.main.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.validation.New;
import ru.kanban.main.dto.validation.Update;

import javax.validation.constraints.Pattern;

/**
 * The type Comment create update dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateUpdateDto {
    @Pattern(regexp = "[0-9a-zA-Zа-яА-Я-@#$.,%^&+=!\\s]{2,600}$",
            message = "Длина текста в отзыве должна быть от 2 до 600 символов.",
            groups = {New.class, Update.class})
    @Schema(description = "Текст комментария")
    String text;
}
