package com.example.patient.appointment.system.exception;

public class ConflictFieldException extends RuntimeException{
    public ConflictFieldException(String message) {
        super(message);
    }
}
