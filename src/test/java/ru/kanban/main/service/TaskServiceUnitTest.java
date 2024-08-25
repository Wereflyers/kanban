package ru.kanban.main.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.Status;
import ru.kanban.main.model.Task;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.TaskRepository;
import ru.kanban.main.service.impl.TaskServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {
    @Mock
    private UserService userService;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    private static User user;
    private static Task task;
    @Captor
    private ArgumentCaptor<Task> captor;

    @BeforeEach
    void init() {
        user = User.builder().id(1L).build();
        task = Task.builder()
                .id(1L)
                .name("task1")
                .description("product1 description")
                .author(user)
                .status(Status.NEW)
                .build();
    }

    @Test
    void createTask_shouldReturnTask() {
        // given
        Task request = Task.builder()
                .name("task1")
                .description("product1 description")
                .build();
        task.setId(null);

        // when
        when(userService.getUser(user.getId())).thenReturn(user);

        // then
        taskService.createTask(request, user.getId());
        verify(taskRepository).save(captor.capture());
        compare(task, captor.getValue());
    }

    @Test
    void getTaskById_shouldThrowException_whenIdInvalid() {
        // when
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(123L));
    }

    @Test
    void updateTaskByAuthor_shouldUpdateCorrectly() {
        // given
        Task updateRequest = Task.builder().name("task").status(Status.IN_PROGRESS).build();
        task.setName(updateRequest.getName());
        task.setStatus(updateRequest.getStatus());

        // when
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // then
        taskService.updateTaskByAuthor(task.getId(), user.getId(), updateRequest);
        verify(taskRepository).save(captor.capture());
        compare(task, captor.getValue());
    }

    @Test
    void updateTaskByAuthor_shouldThrowException_whenUserIsNotAuthor() {
        // given
        long userId = 2L;
        Task updateRequest = Task.builder().name("task").build();

        // when
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        // then
        assertThrows(AccessDenialException.class,
                () -> taskService.updateTaskByAuthor(task.getId(), userId, updateRequest));
    }

    @Test
    void deleteTaskByAuthor_shouldThrowException_whenTaskNotFound() {
        // given
        long userId = 2L;

        // when
        when(taskRepository.existsById(task.getId())).thenReturn(false);

        // then
        assertThrows(EntityNotFoundException.class,
                () -> taskService.deleteTask(task.getId(), userId));
    }

    private static void compare(Task expected, Task actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAuthor().getId(), actual.getAuthor().getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}
