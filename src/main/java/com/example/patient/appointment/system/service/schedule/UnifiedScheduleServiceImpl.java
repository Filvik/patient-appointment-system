package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import com.example.patient.appointment.system.model.schedule.ScheduleRequestOfWeek;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebService(endpointInterface = "com.example.patient.appointment.system.service.schedule.UnifiedScheduleService")
public class UnifiedScheduleServiceImpl implements UnifiedScheduleService{

    private final ScheduleDayService scheduleDayService;
    private final ScheduleWeekService scheduleWeekService;
    private final ScheduleDifficultWeekService scheduleDifficultWeekService;

    public void createTimeSlotsForDay(ScheduleRequest request) {
        scheduleDayService.createTimeSlotsForDay(request);
    }

    public void createTimeSlotsForWeek(ScheduleRequest request) {
        scheduleWeekService.createTimeSlotsForWeek(request);
    }

    public void createTimeSlotsForDifficultWeek(ScheduleRequestOfWeek request) {
        scheduleDifficultWeekService.createTimeSlotsForDifficultWeek(request);
    }
}
