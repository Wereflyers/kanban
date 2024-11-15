package ru.t1.main.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskListResponseDto {
    private List<TaskResponseDto> tasks;
    private Long totalTasks;
}