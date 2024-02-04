package com.example.patient.appointment.system.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обработка кастомного исключения SlotNotFoundException
    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<?> handleSlotNotFoundException(SlotNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Обработка кастомного исключения SlotIsBusyException
    @ExceptionHandler(SlotIsBusyException.class)
    public ResponseEntity<?> handleSlotIsBusyException(SlotIsBusyException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    // Обработка IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Обработка PatientNotFoundException
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<?> handlePatientNotFoundException(PatientNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Обработка EmptyFieldException
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<?> handleEmptyFieldException(EmptyFieldException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Обработка ConflictFieldException
    @ExceptionHandler(ConflictFieldException.class)
    public ResponseEntity<?> handleConflictFieldException(ConflictFieldException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    // Обработка других исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred",
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Класс для деталей ошибки
    @Data
    static class ErrorDetails {
        private int status;
        private String message;
        private String details;

        public ErrorDetails(int status, String message, String details) {
            this.status = status;
            this.message = message;
            this.details = details;
        }
    }
}
