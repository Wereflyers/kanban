package ru.kanban.main.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.User;


/**
 * The interface User mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    /**
     * User dto to user user.
     *
     * @param userCreateDto the user create dto
     * @return the user
     */
    User userDtoToUser(UserCreateDto userCreateDto);

    /**
     * User to user response dto user response dto.
     *
     * @param user the user
     * @return the user response dto
     */
    UserResponseDto userToUserResponseDto(User user);
}
