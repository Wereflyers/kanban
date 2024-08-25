package ru.kanban.main.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The type Jwt auth request.
 */
@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthRequest {
    @Schema(description = "Электронная почта")
    @NotBlank(message = "Необходимо указать адрес электронной почты")
    @Email(message = "Email должен быть корректным адресом электронной почты")
    @Length(min = 6, max = 30, message = "Адрес электронной почты должен содержать от 6 до 30 символов")
    String email;
    @Schema(description = "Пароль")
    @NotBlank(message = "Необходимо указать пароль")
    String password;
}