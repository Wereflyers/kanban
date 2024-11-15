package ru.t1.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.main.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
