package ru.kanban.main.web.controller.comment;

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
import ru.kanban.main.dto.comment.CommentListResponseDto;
import ru.kanban.main.dto.comment.CommentResponseDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.web.controller.AbstractControllerTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("/data-test.sql")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private CommentResponseDto commentResponseDto;

    @BeforeEach
    void init() {
        commentResponseDto = CommentResponseDto.builder()
                .id(1L)
                .author(new UserResponseDto(1L, "user1@email.ru"))
                .text("very hard")
                .task(getTask(2L))
                .date(LocalDateTime.of(2023, 11, 15, 12, 34, 56))
                .build();
    }

    @Test
    @SneakyThrows
    void getCommentTest() {
        CommentResponseDto responseDto = getComment(1L);

        assertEquals(commentResponseDto, responseDto);
    }

    @Test
    @SneakyThrows
    void getAllCommentsTest() {
        CommentListResponseDto responseDto = getAllComments(1L);

        assertEquals(0, responseDto.getComments().size());
    }

    @SneakyThrows
    public CommentListResponseDto getAllComments(long taskId) {
        MvcResult result = mockMvc.perform(get("/comment/task/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CommentListResponseDto.class);
    }
}
