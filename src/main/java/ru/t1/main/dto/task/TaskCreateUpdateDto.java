package ru.t1.main.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.t1.main.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskCreateUpdateDto {
    private String name;
    private String description;
    private Status status;
}
