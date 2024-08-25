package ru.kanban.main.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * The type Error response.
 */
@Builder
@Getter
public class ErrorResponse {
    @Schema(description = "Сообщение об ошибке")
    private String message;
    @Schema(description = "Название исключения")
    private String error;
    @Schema(description = "Статус")
    private String status;
    @Schema(description = "Время возникновения ошибки")
    private LocalDateTime timestamp;
}
