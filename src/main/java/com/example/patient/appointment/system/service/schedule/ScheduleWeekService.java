package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.schedule.EnumDayOfWeek;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService(serviceName = "ScheduleWeekService")
@RequiredArgsConstructor
public class ScheduleWeekService {

    private final TimeSlotService timeSlotService;

    /**
     * Сервис для создания временных слотов на прием для каждого дня в указанной неделе.
     * Данный веб-сервис позволяет генерировать расписание временных слотов на основе запроса, который содержит
     * параметры необходимые для формирования расписания на определенную неделю.
     * <p>
     * Процесс создания слотов происходит путем итерации по всем дням недели и генерации временных слотов
     * для каждого дня на основе предоставленных в запросе параметров.
     * <p>
     * В конце процесса возвращает список всех сгенерированных временных слотов для удобства последующей обработки или отображения.
     *
     * @param request Объект {@link ScheduleRequest}, содержащий параметры для генерации расписания,
     *                включая дату начала недели, продолжительность слотов, перерывы и рабочие часы.
     * @return Список {@link TimeSlot}, содержащий все временные слоты, сгенерированные для каждого дня недели
     *         на основе предоставленного запроса.
     */
    @WebMethod
    public List<TimeSlot> createTimeSlotsForWeek(ScheduleRequest request) {
        List<TimeSlot> slots = new ArrayList<>();
        for (EnumDayOfWeek dayOfWeek : EnumDayOfWeek.values()) {
            LocalDate bookingDate = calculateDateForDayOfWeek(request.getBookingDate(), dayOfWeek);
            request.setBookingDate(bookingDate);
            List<TimeSlot> dailySlots = timeSlotService.getTimeSlotsForScheduleRequest(request);
            slots.addAll(dailySlots);
        }
        return slots;
    }

    /**
     * Вычисляет дату для указанного дня недели, исходя из опорной даты.
     * Этот метод позволяет найти ближайший день, соответствующий указанному дню недели, начиная с опорной даты.
     * <p>
     * Метод учитывает, что если опорная дата уже соответствует искомому дню недели, то возвращается она сама.
     * В противном случае, метод ищет следующую дату, которая соответствует указанному дню недели.
     *
     * @param referenceDate Опорная дата, с которой начинается поиск.
     * @param dayOfWeek Целевой день недели, для которого требуется найти дату.
     * @return {@link LocalDate} - дата, соответствующая искомому дню недели.
     */
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
