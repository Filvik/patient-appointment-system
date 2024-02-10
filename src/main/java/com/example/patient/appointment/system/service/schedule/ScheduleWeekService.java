package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService(serviceName = "ScheduleWeekService")
@RequiredArgsConstructor
public class ScheduleWeekService {

    private final TimeSlotService timeSlotService;

    /**
     * Генерирует список временных слотов для каждого дня в указанный период на основе единого расписания.
     * <p>
     * Этот метод принимает {@link ScheduleRequest}, который содержит параметры для генерации временных слотов,
     * такие как начальная дата недели, продолжительность каждого слота, время обеденного перерыва и общее рабочее время.
     * Используя эти параметры, метод генерирует и возвращает список временных слотов для каждого дня недели, начиная
     * с даты, указанной в запросе, и продолжая в течение определенного количества дней.
     * <p>
     * Процесс создания слотов включает в себя итерацию по каждому дню в заданном диапазоне дат, генерацию временных слотов
     * на основе предоставленных параметров для каждого дня, и сбор этих слотов в общий список.
     * <p>
     *
     * @param request Объект {@link ScheduleRequest} с параметрами для генерации расписания на неделю.
     */
    @WebMethod
    public void createTimeSlotsForWeek(ScheduleRequest request) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalDate start = request.getBookingDate();
        LocalDate end = start.plusDays(7); // Пример для недели
        while (!start.isAfter(end)) {
            request.setBookingDate(start);
            slots.addAll(timeSlotService.getTimeSlotsForScheduleRequest(request));
            start = start.plusDays(1);
        }
        timeSlotService.saveAllTimeSlots(slots);
    }
}
