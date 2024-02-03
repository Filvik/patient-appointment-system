package com.example.patient.appointment.system.schedule.service;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.repository.DoctorRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import com.example.patient.appointment.system.schedule.ScheduleRequest;
import com.example.patient.appointment.system.schedule.ScheduleService;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService(serviceName = "ScheduleService")
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final TimeSlotRepository timeSlotRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<TimeSlot> createTimeSlots(ScheduleRequest request) {

        List<TimeSlot> slots = new ArrayList<>();

        LocalTime currentTime = request.getStartWorkTime();
        final int slotDuration = request.getSlotDurationInMinutes();
        final int breakDuration = request.getBreakForLunchInMinutes();
        final int workingHours = request.getWorkingTimeInHoursInDay();
        final LocalDate date = request.getBookingDate();
        final int totalWorkingMinutes = workingHours * 60;
        final int totalSlots = (totalWorkingMinutes - breakDuration) / slotDuration;

        if (totalSlots <= 0 || date == null) {
            log.error("Некорректные данные для создания слотов: {}", request);
            return slots;
        }

        var doctorOptional = doctorRepository.findById(request.getDoctorId());
        if (doctorOptional.isEmpty()) {
            log.error("Врач с ID: {} не найден", request.getDoctorId());
            return slots;
        }

        boolean lunchBreakAdded = false;
        for (int i = 0; i < totalSlots; i++) {
            if (i == totalSlots / 2 && !lunchBreakAdded) {
                currentTime = currentTime.plusMinutes(breakDuration);
                lunchBreakAdded = true;
            }

            TimeSlot slot = new TimeSlot();
            slot.setStartTime(currentTime);
            currentTime = currentTime.plusMinutes(slotDuration);
            slot.setEndTime(currentTime);
            slot.setDate(request.getBookingDate());
            slot.setDoctor(doctorOptional.get());

            slots.add(slot);
        }
        timeSlotRepository.saveAllAndFlush(slots);
        return slots;
    }
}
