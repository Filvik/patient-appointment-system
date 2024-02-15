package com.example.patient.appointment.system.exception;

public class TransactionRollbackException extends RuntimeException {
    public TransactionRollbackException(String message, Throwable cause) {
        super(message, cause);
    }
}

