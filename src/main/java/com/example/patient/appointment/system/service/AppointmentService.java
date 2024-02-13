package com.example.patient.appointment.system.service;

import com.example.patient.appointment.system.adapter.TimeSlotConverter;
import com.example.patient.appointment.system.exception.PatientNotFoundException;
import com.example.patient.appointment.system.exception.SlotIsBusyException;
import com.example.patient.appointment.system.exception.SlotNotFoundException;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.TimeSlotDTO;
import com.example.patient.appointment.system.repository.PatientRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final TimeSlotRepository timeSlotRepository;
    private final PatientRepository patientRepository;

    /**
     * Найти доступные слоты для заданного врача и даты.
     *
     * @param doctorId Идентификатор врача.
     * @param date     Дата, на которую нужно найти слоты.
     * @return Список доступных временных слотов.
     */
    public List<TimeSlotDTO> findAvailableSlots(Long doctorId, LocalDate date) {
        log.info("Поиск доступных слотов для врача с ID: {} на дату: {}", doctorId, date);
        List<TimeSlot> timeSlotEntities = timeSlotRepository.findByDoctorIdAndDate(doctorId, date)
                .stream()
                .filter(slot -> slot.getPatient() == null)
                .collect(Collectors.toList());
        if (!timeSlotEntities.isEmpty()) {
            log.info("Доступные слоты для врача с ID: {} на дату: {} были найдены", doctorId, date);
            return TimeSlotConverter.convertToDTO(timeSlotEntities);
        } else {
            log.info("Доступные слоты для врача с ID: {} на дату: {} не найдены", doctorId, date);
            return Collections.emptyList();
        }
    }


    /**
     * Забронировать слот.
     *
     * @param slotId    Идентификатор слота, который нужно забронировать.
     * @param patientId Идентификатор пациента, который забронировал слот.
     * @return Забронированный временной слот.
     * @throws SlotNotFoundException    если слот не найден.
     * @throws IllegalArgumentException если `slotId` или `patientId` являются null.
     * @throws PatientNotFoundException если пациент не найден.
     */
    public TimeSlot bookSlot(Long slotId, Long patientId) {
        if (slotId == null || patientId == null) {
            log.info("Идентификатор слота или пациента не должны быть null");
            throw new IllegalArgumentException("Идентификатор слота или пациента не должны быть null");
        }

        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Слот с ID: " + slotId + " не найден"));
        log.info("Бронирование слота с ID: {}", slotId);

        var patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            log.info("Пациент с ID: {} не найден", patientId);
            throw new PatientNotFoundException("Пациент с ID: " + patientId + " не найден");
        }

        if (slot.getPatient() == null) {
            slot.setPatient(patientOpt.get());
            timeSlotRepository.saveAndFlush(slot);
            log.info("Слот с ID: {} успешно забронирован", slotId);
        } else {
            log.info("Слот с ID: {} уже был забронирован", slotId);
            throw new SlotIsBusyException("Слот с ID: " + slotId + " уже был забронирован");
        }
        return slot;
    }

    /**
     * Найти слоты, забронированные определенным пациентом.
     *
     * @param patientId Уникальный идентификатор пациента.
     * @return Список временных слотов, забронированных пациентом.
     */
    public List<TimeSlot> findSlotsByPatientById(Long patientId) {
        if (patientRepository.findById(patientId).isEmpty()) {
            log.info("Пациент с ID: {} не найден", patientId);
            throw new PatientNotFoundException("Пациент с ID: " + patientId + " не найден");
        }
        log.info("Поиск слотов для пациента с patientId: {}", patientId);
        List<TimeSlot> timeSlotEntities = timeSlotRepository.findSlotsByPatientId(patientId);
        if (!timeSlotEntities.isEmpty()) {
            log.info("Слоты, забронированные пациентом с patientId: {} были найдены", patientId);
            return timeSlotEntities;
        } else {
            log.info("Слоты, забронированные пациентом с patientId: {} не найдены", patientId);
            return Collections.emptyList();
        }
    }

    /**
     * Найти слоты, забронированные определенным пациентом по его UUID.
     *
     * @param uuid Уникальный идентификатор пациента (UUID).
     * @return Список временных слотов, забронированных пациентом.
     */
    public List<TimeSlot> findSlotsByPatientByUuid(UUID uuid) {
        if (patientRepository.findByUuid(uuid).isEmpty()) {
            log.info("Пациент с UUID: {} не найден", uuid);
            throw new PatientNotFoundException("Пациент с UUID: " + uuid + " не найден");
        }
        log.info("Поиск слотов для пациента с UUID: {}", uuid);
        List<TimeSlot> timeSlotEntities = timeSlotRepository.findByPatientUuid(uuid);
        if (!timeSlotEntities.isEmpty()) {
            log.info("Слоты, забронированные пациентом с UUID: {} были найдены", uuid);
            return timeSlotEntities;
        } else {
            log.info("Слоты, забронированные пациентом с UUID: {} не найдены", uuid);
            return Collections.emptyList();
        }
    }
}
