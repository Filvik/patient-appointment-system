package com.example.patient.appointment.system.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionManagementAspect {

//    @Before("execution(* com.example.patient.appointment.system.service.AppointmentService.*(..))")
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction(JoinPoint joinPoint) {
        System.out.println("Before transaction: " + joinPoint.getSignature().getName());
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Transaction started");
        Object result = joinPoint.proceed();
        System.out.println("Transaction committed with result: " + result);
        return result;
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterTransaction(JoinPoint joinPoint) {
        System.out.println("After transaction: " + joinPoint.getSignature().getName());
    }
}
