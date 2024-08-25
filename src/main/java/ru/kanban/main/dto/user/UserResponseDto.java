package ru.kanban.main.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * The type User response dto.
 */
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    @Schema(description = "Идентификатор пароля")
    Long id;
    @Schema(description = "Электронная почта")
    String email;
}
