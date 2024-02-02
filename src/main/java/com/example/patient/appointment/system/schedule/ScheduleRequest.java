package com.example.patient.appointment.system.schedule;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private Long doctorId;
    private LocalDate date;
    private LocalTime startWorkTime;
    private int slotDurationInMinutes;
    private int breakForLunchInMinutes;
    private int workingTimeInHoursInDay;
}