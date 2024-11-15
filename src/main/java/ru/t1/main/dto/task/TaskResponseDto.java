package ru.t1.main.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.t1.main.model.Status;

/**
 * The type Task response dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskResponseDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
}
