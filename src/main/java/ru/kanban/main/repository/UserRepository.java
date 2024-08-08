package ru.kanban.main.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kanban.main.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
