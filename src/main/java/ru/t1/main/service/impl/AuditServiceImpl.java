package ru.t1.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.main.model.Log;
import ru.t1.main.repository.LogRepository;
import ru.t1.main.service.AuditService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final LogRepository logRepository;

    public void log(Log log) {
        logRepository.save(log);
    }
}
