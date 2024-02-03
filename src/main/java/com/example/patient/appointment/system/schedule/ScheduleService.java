package com.example.patient.appointment.system.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface ScheduleService {
    @WebMethod
    List<TimeSlot> createTimeSlots(ScheduleRequest request);
}