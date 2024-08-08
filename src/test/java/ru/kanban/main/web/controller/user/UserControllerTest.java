package ru.kanban.main.web.controller.user;

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
import ru.kanban.main.dto.user.JwtAuthRequest;
import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.web.controller.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/data-test.sql")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private UserCreateDto userDto;

    @BeforeEach
    void init() {
        userDto = UserCreateDto.builder()
                .email("buyer1@email.ru")
                .password("1111111111")
                .confirmPassword("1111111111")
                .build();
    }

    @Test
    @SneakyThrows
    void createNewUserUserTest() {
        UserResponseDto responseDto = createUser(userDto);

        assertEquals(userDto.getEmail(), responseDto.getEmail());
        assertNotNull(responseDto.getId());
    }

    @Test
    @SneakyThrows
    void createNewUserUserTest_whenPasswordsNotEqual() {
        userDto.setConfirmPassword("1");
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void authUserTest() {
        createUser(userDto);
        JwtAuthRequest jwtAuthRequest = new JwtAuthRequest(userDto.getEmail(), userDto.getPassword());

        String token = authUser(jwtAuthRequest);

        assertNotNull(token);
    }

    @Test
    @SneakyThrows
    void authUserTest_whenWrongPassword() {
        createUser(userDto);
        JwtAuthRequest jwtAuthRequest = new JwtAuthRequest(userDto.getEmail(), "wrong");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtAuthRequest)))
                .andExpect(status().isConflict());
    }

    @SneakyThrows
    public UserResponseDto createUser(UserCreateDto userDto) {
        MvcResult result = mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDto.class);
    }

    @SneakyThrows
    public String authUser(JwtAuthRequest request) {
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }
}
