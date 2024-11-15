package ru.t1.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.main.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
