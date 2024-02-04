package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.repository.DoctorRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSlotService {
    private final DoctorRepository doctorRepository;
    private final TimeSlotRepository timeSlotRepository;

    /**
     * Генерирует временные слоты для заданного расписания.
     * <p>
     * На основе предоставленного {@link ScheduleRequest} метод генерирует список временных слотов,
     * учитывая время начала работы, продолжительность каждого слота, время обеденного перерыва,
     * общее рабочее время в день и дату. Если входные данные некорректны или врач с заданным ID не найден,
     * возвращается пустой список.
     *
     * @param request объект {@link ScheduleRequest}, содержащий параметры для генерации временных слотов.
     * @return список сгенерированных временных слотов {@link TimeSlot}, или пустой список, если слоты не могут быть созданы.
     */
    public List<TimeSlot> getTimeSlotsForScheduleRequest(ScheduleRequest request) {
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
            return Collections.emptyList();
        }

        var doctorOptional = doctorRepository.findById(request.getDoctorId());
        if (doctorOptional.isEmpty()) {
            log.error("Врач с ID: {} не найден", request.getDoctorId());
            return Collections.emptyList();
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
        return slots;
    }

    /**
     * Сохраняет список временных слотов в базе данных.
     * <p>
     * Принимает список временных слотов {@link TimeSlot} и сохраняет их в базе данных,
     * используя {@link TimeSlotRepository}. Все слоты сохраняются и фиксируются в одной транзакции.
     *
     * @param slots список временных слотов для сохранения.
     */
    public void saveAllTimeSlots(List<TimeSlot> slots) {
        timeSlotRepository.saveAllAndFlush(slots);
    }

}
