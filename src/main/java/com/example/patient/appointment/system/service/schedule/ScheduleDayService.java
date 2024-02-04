package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@WebService(serviceName = "ScheduleDayService")
@RequiredArgsConstructor
public class ScheduleDayService {

    private final TimeSlotService timeSlotService;

    /**
     * Создает временные слоты на основе предоставленного запроса расписания для конкретного дня.
     * Этот метод веб-сервиса принимает запрос {@link ScheduleRequest}, который содержит информацию
     * о дате, времени начала работы, продолжительности слота, перерыве на обед и общем рабочем времени в день.
     * На основе этих данных метод генерирует список временных слотов для записи на прием к врачу.
     * <p>
     * Процесс создания слотов включает в себя два основных шага:
     * 1. Генерация списка временных слотов с использованием сервиса {@link TimeSlotService}.
     * 2. Сохранение всех сгенерированных временных слотов в базе данных.
     * <p>
     * В конце метод возвращает список созданных временных слотов.
     *
     * @param request Объект запроса {@link ScheduleRequest}, содержащий параметры для генерации временных слотов.
     * @return Список {@link TimeSlot} - временных слотов, созданных на основе параметров запроса.
     * @see ScheduleRequest - класс запроса, содержащий параметры для расписания.
     * @see TimeSlot - класс временного слота, представляющий собой талон на прием.
     */
    @WebMethod
    public List<TimeSlot> createTimeSlotsForDay(ScheduleRequest request) {
        List<TimeSlot> slots = timeSlotService.getTimeSlotsForScheduleRequest(request);
        timeSlotService.saveAllTimeSlots(slots);
        return slots;
    }
}
