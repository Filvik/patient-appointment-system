package com.example.patient.appointment.system.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeSlotDTO {
    private Long id;
    private String doctorFullName;
    private String patientFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
}
