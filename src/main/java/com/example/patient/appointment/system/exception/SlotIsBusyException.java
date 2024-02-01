package com.example.patient.appointment.system.exception;

public class SlotIsBusyException extends RuntimeException {
    public SlotIsBusyException(String message) {
        super(message);
    }
}
