package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import com.example.patient.appointment.system.model.schedule.ScheduleRequestOfWeek;
import jakarta.jws.WebService;

@WebService
public interface UnifiedScheduleService {
    // Методы из ScheduleDayService
    abstract void createTimeSlotsForDay(ScheduleRequest request);
    // Методы из ScheduleWeekService
    abstract void createTimeSlotsForWeek(ScheduleRequest request);
    // Методы из ScheduleDifficultWeekService
    abstract void createTimeSlotsForDifficultWeek(ScheduleRequestOfWeek request);
}
