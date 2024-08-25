package ru.kanban.main.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kanban.main.model.User;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);
}
