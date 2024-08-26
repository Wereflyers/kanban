package ru.kanban.main.web.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.kanban.main.dto.task.TaskListResponseDto;
import ru.kanban.main.dto.task.TaskResponseDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.Status;
import ru.kanban.main.web.controller.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("/data-test.sql")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private TaskResponseDto taskResponseDto;

    @BeforeEach
    void init() {
        taskResponseDto = TaskResponseDto.builder()
                .id(1L)
                .name("task1")
                .description("product1 description")
                .author(new UserResponseDto(1L, "user1@email.ru"))
                .status(Status.NEW)
                .build();
    }

    @Test
    @SneakyThrows
    void getTask_shouldReturnTaskResponseDto() {
        TaskResponseDto savedTask = getTask(1L);

        assertEquals(taskResponseDto, savedTask);
    }

    @Test
    @SneakyThrows
    void getAllTasksTest() {
        TaskListResponseDto responseDto = getAllTasks();

        assertEquals(2, responseDto.getTotalTasks());
        assertEquals(1L, responseDto.getTasks().get(0).getId());
        assertEquals("task1", responseDto.getTasks().get(0).getName());
        assertEquals(2L, responseDto.getTasks().get(1).getId());
        assertEquals("task2", responseDto.getTasks().get(1).getName());
    }

    @Test
    @SneakyThrows
    void getAllTasksByAuthorTest() {
        TaskListResponseDto responseDto = getAllTasksByAuthorWithComments(1L);

        assertEquals(1, responseDto.getTotalTasks());
        assertEquals(1L, responseDto.getTasks().get(0).getId());
        assertEquals("task1", responseDto.getTasks().get(0).getName());
        assertEquals(1L, responseDto.getTasks().get(0).getAuthor().getId());
    }

    @Test
    @SneakyThrows
    void getAllTasksByExecutorTest() {
        TaskListResponseDto responseDto = getAllTasksByExecutorWithComments(1L);

        assertEquals(1, responseDto.getTotalTasks());
        assertEquals(2L, responseDto.getTasks().get(0).getId());
        assertEquals("task2", responseDto.getTasks().get(0).getName());
        assertEquals(1L, responseDto.getTasks().get(0).getExecutor().getId());
    }

    @SneakyThrows
    public TaskListResponseDto getAllTasks() {
        MvcResult result = mockMvc.perform(post("/task/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskListResponseDto.class);
    }

    @SneakyThrows
    public TaskListResponseDto getAllTasksByAuthorWithComments(long authorId) {
        MvcResult result = mockMvc.perform(get("/task/author/{authorId}", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskListResponseDto.class);
    }

    @SneakyThrows
    public TaskListResponseDto getAllTasksByExecutorWithComments(long executorId) {
        MvcResult result = mockMvc.perform(get("/task/executor/{executorId}", executorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskListResponseDto.class);
    }
}
