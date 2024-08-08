package ru.kanban.main.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.kanban.main.web.controller.validation.PasswordNotContainsEmail;
import ru.kanban.main.web.controller.validation.PasswordSameConfirmPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@PasswordNotContainsEmail
@PasswordSameConfirmPassword
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDto {
    @NotBlank(message = "Необходимо указать адрес электронной почты")
    @Pattern(regexp = "^([a-zA-Z0-9._-]){2,17}+(@[a-zA-Z]{2,8})+(\\.[a-zA-Z]{2,3})$",
            message = "Длина почты должна быть от 8 до 30 символов.")
    String email;
    @NotBlank(message = "Необходимо указать пароль")
    String password;
    @NotBlank(message = "Необходимо повторно указать пароль")
    String confirmPassword;
}
