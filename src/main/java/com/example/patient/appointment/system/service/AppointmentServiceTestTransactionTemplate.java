package com.example.patient.appointment.system.service;

import com.example.patient.appointment.system.model.Patient;
import com.example.patient.appointment.system.model.ResponseForSpecificPatient;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.repository.PatientRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceTestTransactionTemplate {

    private final TimeSlotRepository timeSlotRepository;
    private final PatientRepository patientRepository;
    private final TransactionTemplate transactionTemplate;

    /**
     * Забронировать слот для конкретного пациента.
     * <p>
     * Этот метод принимает данные пациента в формате JSON и производит бронирование временного слота.
     * Если пациент уже существует в базе данных, используется его существующая запись.
     * В противном случае, в базе данных создаётся новая запись пациента.
     * <p>
     * Метод возвращает объект {@link ResponseForSpecificPatient}, который содержит информацию
     * о том существует ли пациент в базе данных, был ли он успешно добавлен и забронирован ли слот.
     *
     * @param patient Данные пациента, которые нужно добавить в базу данных.
     * @param slotId  Идентификатор слота, который нужно забронировать.
     * @return Объект {@link ResponseForSpecificPatient}, содержащий информацию о добавлении пациента и бронировании слота.
     */
    public ResponseForSpecificPatient bookSlotForSpecificPatient(Patient patient, Long slotId) {

        ResponseForSpecificPatient response = new ResponseForSpecificPatient(false, false, false, "");

        // Первая часть: Добавление пациента в базу данных
        Patient savedPatient = transactionTemplate.execute(status -> {
            Optional<Patient> existingPatient = patientRepository.findByFullNameAndDateOfBirth(patient.getFullName(), patient.getDateOfBirth());
            if (existingPatient.isPresent()) {
                response.setExisted(true);
                return existingPatient.get();
            } else {
                response.setAdded(true);
                return patientRepository.saveAndFlush(patient);

            }
        });

        // Вторая часть: Попытка бронирования слота
        Boolean slotBooked = transactionTemplate.execute(status -> {
            if (slotId == null) {
                status.setRollbackOnly();
                response.setDescription("Идентификатор слота не должен быть null");
                return false;
            }

            // Поиск слота по идентификатору
            Optional<TimeSlot> slotOptional = timeSlotRepository.findById(slotId);
            if (slotOptional.isEmpty()) {
                status.setRollbackOnly();
                response.setDescription("Слот с ID: " + slotId + " не найден");
                return false;
            }

            // Попытка занять слот
            TimeSlot slot = slotOptional.get();
            if (slot.getPatient() != null) {
                status.setRollbackOnly();
                response.setDescription("Слот уже занят другим пациентом.");
                return false;
            }

            // Успешное бронирование слота
            slot.setPatient(savedPatient);
            timeSlotRepository.saveAndFlush(slot);
            response.setDescription("Слот успешно забронирован");
            return true;
        });

        // Установка результата операции
        response.setIsSlotBooked(slotBooked);
        return response;
    }

}
