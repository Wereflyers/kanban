package ru.kanban.main.dto.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.dto.validation.New;
import ru.kanban.main.dto.validation.Update;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateUpdateDto {
    @Pattern(regexp = "[0-9a-zA-Zа-яА-Я-@#$.,%^&+=!\\s]{2,600}$",
            message = "Длина текста в отзыве должна быть от 2 до 600 символов.",
            groups = {New.class, Update.class})
    String text;
}
