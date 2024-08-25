package ru.kanban.main.service;

import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.User;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Create new user.
     *
     * @param userCreateDto the user create dto
     * @return the user response dto
     */
    UserResponseDto createNewUser(UserCreateDto userCreateDto);

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    User getUser(long userId);
}