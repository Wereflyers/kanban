package ru.t1.main.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggableAspect {

    @AfterThrowing(pointcut = "execution(* ru.t1.main..*(..))", throwing = "ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());
        log.error("Write something in the log... We have caught exception in method: "
                + methodName + " with arguments "
                + arguments + "\nand the full toString: " + stuff + "\nthe exception is: "
                + ex.getMessage());
    }

    @AfterReturning(pointcut = "execution(* ru.t1.main.service..*(..))", returning = "result")
    public void logAfterReturning(Object result) {
        log.info("Method was completed successfully. The result is " + result);
    }

    @Before("execution(* ru.t1.main.service..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Calling: " + joinPoint.getSignature().getName());
    }
}