package ru.t1.main.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.t1.main.aspect.annotation.Auditable;
import ru.t1.main.model.Log;
import ru.t1.main.service.AuditService;

import java.time.LocalDate;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class AuditableAspect {
    private final AuditService auditService;

    @Pointcut("@annotation(ru.t1.main.aspect.annotation.Auditable)")
    public void auditableMethod() {
    }

    @Around("auditableMethod() && within(@org.springframework.web.bind.annotation.RestController *)")
    public Object auditableMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Auditable.class).methodName();
        log.info("AUDITING " + methodName);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        String username = request.getHeader("X-Sharer-User-Id");
        if (username == null) {
            username = "Unauthorized user";
        }

        auditService.log(Log.builder()
                .user(username)
                .date(LocalDate.now())
                .entry(methodName).build());

        return joinPoint.proceed();
    }
}
