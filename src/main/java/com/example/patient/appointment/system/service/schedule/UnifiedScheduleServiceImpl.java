package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.schedule.BookingResponse;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import com.example.patient.appointment.system.model.schedule.ScheduleRequestOfWeek;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@WebService(serviceName = "UnifiedScheduleService")
@Slf4j
public class UnifiedScheduleServiceImpl implements UnifiedScheduleService {

    private final ScheduleDayService scheduleDayService;
    private final ScheduleWeekService scheduleWeekService;
    private final ScheduleDifficultWeekService scheduleDifficultWeekService;

    @Override
    @WebMethod(operationName = "createTimeSlotsForDay")
    @WebResult(partName = "response")
    public BookingResponse createTimeSlotsForDayWithResponse(ScheduleRequest request) {
        scheduleDayService.createTimeSlotsForDay(request);
        log.info("Slots for day created successfully.");
        return new BookingResponse("Success", "Slots for day created successfully.");
    }

    @Override
    @WebMethod(operationName = "createTimeSlotsForWeek")
    @WebResult(partName = "response")
    public BookingResponse createTimeSlotsForWeekWithResponse(ScheduleRequest request) {
        scheduleWeekService.createTimeSlotsForWeek(request);
        log.info("Slots for week created successfully.");
        return new BookingResponse("Success", "Slots for week created successfully.");
    }

    @Override
    @WebMethod(operationName = "createTimeSlotsForDifficultWeek")
    @WebResult(partName = "response")
    public BookingResponse createTimeSlotsForDifficultWeekWithResponse(ScheduleRequestOfWeek request) {
        scheduleDifficultWeekService.createTimeSlotsForDifficultWeek(request);
        log.info("Slots for difficult week created successfully.");
        return new BookingResponse("Success", "Slots for difficult week created successfully.");
    }

}
