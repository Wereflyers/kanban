package ru.kanban.main.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.model.User;


@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    User userDtoToUser(UserCreateDto userCreateDto);

    UserResponseDto userToUserResponseDto(User user);
}
