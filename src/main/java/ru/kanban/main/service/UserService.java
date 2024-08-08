package ru.kanban.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.exception.DuplicateException;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.mapper.UserMapper;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.UserRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createNewUser(UserCreateDto userCreateDto) {
        if (checkIfUserExistsByEmail(userCreateDto.getEmail()))
            throw new DuplicateException("User " + userCreateDto.getEmail() + " already exists");
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User response = repository.save(userMapper.userDtoToUser(userCreateDto));
        return userMapper.userToUserResponseDto(response);
    }

    public User getUser(long userId) {
        return repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private boolean checkIfUserExistsByEmail(String email) {
        return (repository.findByEmail(email).isPresent());
    }
}