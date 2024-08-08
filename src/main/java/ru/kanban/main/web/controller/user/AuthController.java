package ru.kanban.main.web.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kanban.main.configuration.config.JwtTokenProvider;
import ru.kanban.main.dto.user.ChangePassRequest;
import ru.kanban.main.dto.user.JwtAuthRequest;
import ru.kanban.main.dto.user.UserCreateDto;
import ru.kanban.main.dto.user.UserResponseDto;
import ru.kanban.main.dto.validation.Update;
import ru.kanban.main.exception.AccessDenialException;
import ru.kanban.main.mapper.UserMapper;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.UserRepository;
import ru.kanban.main.service.UserDetailsChangeService;
import ru.kanban.main.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserDetailsChangeService userDetailsChangeService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/login")
    public String authorization(@RequestBody @Valid JwtAuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = repository.findByEmail(request.getEmail()).orElseThrow(() ->
                    new EntityNotFoundException("User" + request.getEmail() + " not found"));

            return jwtTokenProvider.generateToken(user.getEmail());
        } catch (AuthenticationException e) {
            throw  new AccessDenialException("Access denied");
        }
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/registration")
    public UserResponseDto createNewUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return userService.createNewUser(userCreateDto);
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/user/change/pass")
    public UserResponseDto changePassword(@RequestBody @Validated(Update.class) ChangePassRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getOldPass()));
        return userMapper.userToUserResponseDto(userDetailsChangeService.changePass(request));
    }
}