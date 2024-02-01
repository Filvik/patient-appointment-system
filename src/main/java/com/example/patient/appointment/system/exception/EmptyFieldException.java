package com.example.patient.appointment.system.exception;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message) {
        super(message);
    }
}
