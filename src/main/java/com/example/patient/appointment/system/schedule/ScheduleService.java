package com.example.patient.appointment.system.schedule;

import com.example.patient.appointment.system.model.TimeSlot;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ScheduleService {
    @WebMethod
    List<TimeSlot> createTimeSlots(ScheduleRequest request);
}