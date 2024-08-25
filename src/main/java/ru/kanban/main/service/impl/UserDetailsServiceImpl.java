package ru.kanban.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kanban.main.exception.EntityNotFoundException;
import ru.kanban.main.model.UserSecurity;
import ru.kanban.main.repository.UserRepository;

/**
 * The type User details service.
 */
@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserSecurity.fromUser(repository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User " + email + " not found")));
    }
}
