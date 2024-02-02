package com.example.patient.appointment.system.controller;

import com.example.patient.appointment.system.exception.ConflictFieldException;
import com.example.patient.appointment.system.exception.EmptyFieldException;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.service.AppointmentService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Получает список всех свободных временных слотов для указанного врача на заданную дату.
     * Этот метод полезен для нахождения доступных временных интервалов для записи к врачу.
     *
     * @param doctorId Идентификатор врача. Используется для фильтрации слотов по определённому врачу.
     * @param date     Дата, на которую необходимо найти свободные слоты. Дата должна быть в формате ISO.
     * @return Список {@link TimeSlot} объектов, представляющих доступные временные слоты для врача на указанную дату.
     *         Возвращает пустой список, если доступные слоты не найдены.
     */
    @GetMapping(value = "/getAvailableSlots/{doctorId}/{date}")
    @Operation(summary = "Запрос свободных слотов времени", tags = "Контроллер свободных слотов времени")
    public List<TimeSlot> getAvailableSlots(@PathVariable @Parameter(description = "Идентификатор доктора") Long doctorId,
                                            @PathVariable @Parameter(description = "Дата")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Запрос свободных слотов времени для врача с ID = " + doctorId + " на дату " + date);
        return appointmentService.findAvailableSlots(doctorId, date);
    }

    /**
     * Занимает (бронирует) временной слот с указанным идентификатором.
     * Этот метод используется для бронирования конкретного временного слота.
     *
     * @param slotId Идентификатор временного слота, который нужно забронировать.
     * @return Объект {@link TimeSlot}, представляющий забронированный временной слот.
     *         Возвращает обновлённый объект временного слота после бронирования.
     */
    @PostMapping(value = "/bookSlot/{patientId}")
    @Operation(summary = "Занятие слота времени", tags = "Контроллер занятия слота времени")
    public TimeSlot bookTimeSlot(@PathVariable @Parameter(description = "Идентификатор доктора")Long patientId,
                                 @RequestParam @Parameter(description = "Идентификатор слота")Long slotId) {
        log.info("Запрос на занятие слота времени с ID = " + slotId);
        return appointmentService.bookSlot(slotId, patientId);
    }

    /**
     * Получает список всех занятых временных слотов для пациента.
     * Этот метод предоставляет возможность поиска занятых слотов по ID или UUID пациента(ID является приоритетным).
     * Если оба параметра отсутствуют, метод возвращает ошибку HTTP 400 Bad Request.
     *
     * @param patientId  Необязательный параметр для идентификации пациента по его ID.
     *                   Если этот параметр предоставлен, метод ищет слоты, связанные с этим ID.
     * @param patientUuid Необязательный параметр для идентификации пациента по его UUID.
     *                    Если этот параметр предоставлен, метод ищет слоты, связанные с этим UUID.
     * @return Список {@link TimeSlot} объектов, представляющих занятые временные слоты пациента.
     *         Возвращает пустой список, если занятые слоты не найдены.
     * @throws ResponseStatusException если оба параметра (patientId и patientUuid) отсутствуют,
     *                                 возвращает ошибку с кодом HTTP 400 Bad Request.
     */
    @GetMapping(value = "/getBookedSlots")
    @Operation(summary = "Запрос всех занятых слотов времени пациентом", tags = "Контроллер получения занятых слотов времени")
    public List<TimeSlot> getBookedSlotsByPatient(@RequestParam @Parameter(description = "Идентификатор id пациента") Optional<Long> patientId,
                                                  @RequestParam @Parameter(description = "Идентификатор uuid доктора") Optional<UUID> patientUuid) {
        if (patientId.isPresent() && patientUuid.isPresent()) {
            log.info("Необходимо указать что то одно: ID или UUID пациента");
            throw new ConflictFieldException("Необходимо указать что то одно: ID или UUID пациента");
        } else
        if (patientId.isPresent()) {
            log.info("Запрос всех занятых слотов времени для пациента с ID = " + patientId.get());
            return appointmentService.findSlotsByPatientById(patientId.get());
        } else if (patientUuid.isPresent()) {
            log.info("Запрос всех занятых слотов времени для пациента с UUID = " + patientUuid.get());
            return appointmentService.findSlotsByPatientByUuid(patientUuid.get());
        } else {
            log.info("Необходимо указать ID или UUID пациента");
            throw new EmptyFieldException("Необходимо указать ID или UUID пациента");
        }
    }
}