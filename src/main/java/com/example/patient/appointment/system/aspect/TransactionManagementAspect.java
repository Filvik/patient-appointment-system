package com.example.patient.appointment.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionManagementAspect {

    //    @Before("execution(* com.example.patient.appointment.system.service.AppointmentService.*(..))")
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction(JoinPoint joinPoint) {
        log.info("Before transaction: " + joinPoint.getSignature().getName());
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Transaction started");
        Object result = joinPoint.proceed();
        log.info("Transaction committed with result: " + result);
        return result;
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterTransaction(JoinPoint joinPoint) {
        log.info("After transaction: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.patient.appointment.system.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method " + joinPoint.getSignature().getName() + " completed with result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.patient.appointment.system.service.*.*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in method: " + joinPoint.getSignature().getName() + " with message: " + e.getMessage());
    }
}
