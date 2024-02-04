package com.example.patient.appointment.system.schedule.service;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.schedule.request.ScheduleRequest;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface ScheduleService {
    @WebMethod
    List<TimeSlot> createTimeSlots(ScheduleRequest request);
}