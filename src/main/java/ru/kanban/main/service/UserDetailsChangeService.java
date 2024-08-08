package ru.kanban.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.dto.user.ChangePassRequest;
import ru.kanban.main.exception.WrongRegException;
import ru.kanban.main.model.User;
import ru.kanban.main.repository.UserRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsChangeService {
    private final UserRepository repository;
    @Lazy
    private final PasswordEncoder passwordEncoder;

    public User changePass(ChangePassRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new WrongRegException("Passwords do not match");
        }

        User user = getUser(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return repository.save(user);
    }

    private User getUser(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new WrongRegException("User " + email + " not found"));
    }
}
