package com.example.patient.appointment.system.schedule.service;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.repository.DoctorRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import com.example.patient.appointment.system.schedule.enumForSchedule.EnumDayOfWeek;
import com.example.patient.appointment.system.schedule.request.ScheduleRequest;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService(serviceName = "ScheduleService")
@RequiredArgsConstructor
public class ScheduleWeekServiceImpl implements ScheduleService {

    private final DoctorRepository doctorRepository;
    private final ScheduleDayServiceImpl scheduleDayService;

    @Override
    public List<TimeSlot> createTimeSlots(ScheduleRequest request) {
        List<TimeSlot> slots = new ArrayList<>();
        for (EnumDayOfWeek dayOfWeek : EnumDayOfWeek.values()) {
            LocalDate bookingDate = calculateDateForDayOfWeek(request.getBookingDate(), dayOfWeek);
            request.setBookingDate(bookingDate);

            var doctorOptional = doctorRepository.findById(request.getDoctorId());
            if (doctorOptional.isEmpty()) {
                log.error("Врач с ID: {} не найден", request.getDoctorId());
                return slots;
            }

            List<TimeSlot> dailySlots = scheduleDayService.createTimeSlots(request);
            slots.addAll(dailySlots);
        }
        return slots;
    }

    private LocalDate calculateDateForDayOfWeek(LocalDate referenceDate, EnumDayOfWeek dayOfWeek) {
        // Конвертация EnumDayOfWeek в DayOfWeek
        DayOfWeek targetDayOfWeek = DayOfWeek.valueOf(dayOfWeek.name());

        // Если опорная дата уже соответствует искомому дню недели
        if (referenceDate.getDayOfWeek() == targetDayOfWeek) {
            return referenceDate;
        }
        // Вычисляем следующий искомый день недели после опорной даты
        return referenceDate.with(TemporalAdjusters.next(targetDayOfWeek));
    }
}
