package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.schedule.EnumDayOfWeek;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import com.example.patient.appointment.system.model.schedule.ScheduleRequestOfWeek;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@WebService(serviceName = "ScheduleDifficultWeekService")
@RequiredArgsConstructor
public class ScheduleDifficultWeekService {
    private final TimeSlotService timeSlotService;

    /**
     * Сервис для создания временных слотов на сложную неделю, где для каждого дня недели могут быть заданы различные параметры.
     * Позволяет генерировать расписание на период между {@code dateFrom} и {@code dateTo}, включая оба эти дня,
     * с учетом индивидуальных настроек для каждого дня недели, таких как время начала работы, продолжительность слота,
     * время обеденного перерыва и общее рабочее время в день.
     * <p>
     * Для каждого дня в заданном диапазоне дат сервис проверяет, заданы ли для этого дня недели специфические параметры.
     * Если да, то на основе этих параметров генерируются временные слоты для этого дня. Все сгенерированные слоты
     * сохраняются в базе данных.
     * <p>
     * Этот метод веб-сервиса поддерживает гибкое управление расписанием, позволяя задать различные параметры
     * для разных дней недели в рамках одной недели.
     *
     * @param request Объект {@link ScheduleRequestOfWeek}, содержащий детальную информацию о расписании для недели,
     *                включая начальную и конечную даты, общую информацию о продолжительности слотов и перерывов,
     *                а также специальные настройки для каждого дня недели.
     * @return Список {@link TimeSlot} - временных слотов, сгенерированных на основе предоставленного запроса.
     */
    @WebMethod
    public List<TimeSlot> createTimeSlotsForDifficultWeek(ScheduleRequestOfWeek request) {

        LocalDate current = request.getDateFrom();
        List<TimeSlot> slots = new ArrayList<>();
        Map<EnumDayOfWeek, ScheduleRequestOfWeek.ScheduleRequestDayOfWeek> mapOfDay = getMapOfDay(request);
        while (!current.isAfter(request.getDateTo())) {

            EnumDayOfWeek dayOfWeek = EnumDayOfWeek.valueOf(current.getDayOfWeek().name());
            if (mapOfDay.containsKey(dayOfWeek)) {
                ScheduleRequestOfWeek.ScheduleRequestDayOfWeek value = mapOfDay.get(dayOfWeek);
                ScheduleRequest requestForDay = new ScheduleRequest();
                requestForDay.setDoctorId(request.getDoctorId());
                requestForDay.setBookingDate(current);
                requestForDay.setStartWorkTime(value.getStartWorkTime());
                requestForDay.setSlotDurationInMinutes(request.getSlotDurationInMinutes());
                requestForDay.setBreakForLunchInMinutes(value.getBreakForLunchInMinutes());
                requestForDay.setWorkingTimeInHoursInDay(value.getWorkingTimeInHoursInDay());
                slots.addAll(timeSlotService.getTimeSlotsForScheduleRequest(requestForDay));
            }
            current = current.plusDays(1);
        }
        timeSlotService.saveAllTimeSlots(slots);
        return slots;
    }

    /**
     * Преобразует список запросов на расписание по дням недели в карту для быстрого доступа.
     * <p>
     * Этот метод принимает {@link ScheduleRequestOfWeek}, который содержит информацию о расписании
     * на разные дни недели, и преобразует его в карту, где ключом является {@link EnumDayOfWeek},
     * а значением - {@link ScheduleRequestOfWeek.ScheduleRequestDayOfWeek}. Такое представление позволяет
     * легко и быстро получить доступ к параметрам расписания для любого конкретного дня недели.
     *
     * @param request объект {@link ScheduleRequestOfWeek}, содержащий список запросов на расписание
     *                по дням недели.
     * @return карта, ключом которой является день недели {@link EnumDayOfWeek}, а значением -
     *         параметры расписания для этого дня {@link ScheduleRequestOfWeek.ScheduleRequestDayOfWeek}.
     */
    private Map<EnumDayOfWeek, ScheduleRequestOfWeek.ScheduleRequestDayOfWeek> getMapOfDay(ScheduleRequestOfWeek request) {
        Map<EnumDayOfWeek, ScheduleRequestOfWeek.ScheduleRequestDayOfWeek> map = new HashMap<>();
        request.getScheduleRequestOfDayList().forEach(scheduleRequestOfDay
                -> map.put(scheduleRequestOfDay.getDayOfWeek(), scheduleRequestOfDay.getScheduleRequest()));
        return map;
    }
}
