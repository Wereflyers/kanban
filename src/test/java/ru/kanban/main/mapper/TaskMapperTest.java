package ru.kanban.main.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.kanban.main.dto.task.TaskCreateUpdateDto;
import ru.kanban.main.model.Status;
import ru.kanban.main.model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {
    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = Mappers.getMapper(TaskMapper.class);
    }

    @Test
    void testTaskCreateDtoToTask() {
        TaskCreateUpdateDto taskCreateUpdateDto = TaskCreateUpdateDto.builder()
                .name("name")
                .description("someTask")
                .status(Status.IN_PROGRESS)
                .build();

        Task task = taskMapper.taskCreateDtoToTask(taskCreateUpdateDto);

        assertNotNull(task);
        assertEquals(taskCreateUpdateDto.getName(), task.getName());
        assertEquals(taskCreateUpdateDto.getDescription(), task.getDescription());
        assertEquals(taskCreateUpdateDto.getStatus(), task.getStatus());
    }
}
