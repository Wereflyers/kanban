package ru.kanban.main.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * The type Change pass request.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePassRequest {
    @Schema(description = "Электронная почта")
    @NotBlank(message = "Необходимо указать адрес электронной почты")
    String email;
    @Schema(description = "Старый пароль для смены пароля")
    @NotBlank(message = "Необходимо указать пароль")
    String oldPass;
    @Schema(description = "Новый пароль")
    @NotBlank(message = "Необходимо указать пароль")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,40}$", message = "Пароль должен соответствовать следующим требованиям: " +
            "1. Использование строчной буквы;" +
            "2. Использование прописной буквы;" +
            "3. Использование спец.символа \"@,#,$,%,^,&,+,=,!\";" +
            "4. Использование цифры от 0 до 9;" +
            "5. Длина пароля от 8 до 40 символов;")
    String password;
    @Schema(description = "Повторный ввод пароля")
    @NotBlank(message = "Необходимо указать пароль повторно")
    String confirmPassword;
}