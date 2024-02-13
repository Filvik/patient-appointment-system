package com.example.patient.appointment.system.service;

import com.example.patient.appointment.system.exception.SlotIsBusyException;
import com.example.patient.appointment.system.exception.SlotNotFoundException;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.TimeSlotDTO;
import com.example.patient.appointment.system.repository.PatientRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("classpath:data.sql")
@ActiveProfiles("test")
class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    private final Long patientIdInDB = 1L;


    @Test
    void findAvailableSlots() {
        Long doctorId = 101L; // ID врача из тестовых данных
        LocalDate date = LocalDate.of(2024, 1, 1);

        List<TimeSlotDTO> result = appointmentService.findAvailableSlots(doctorId, date);

        // Проверяем, что результат содержит ровно 2 доступных слота
        assertEquals(2, result.size(), "Должно быть найдено 2 доступных слота для врача с ID 101");

        // Проверяем, что все возвращаемые слоты соответствуют заданным критериям
        assertTrue(result.stream().allMatch(slot ->
                slot.getDoctorFullName().equals("Доктор Иванова") &&
                        slot.getDate().equals(date)), "Все найденные слоты должны соответствовать заданному врачу и дате");
    }

    @Test
    void bookAvailableSlot() {
        Long slotId = 11L;

        // Предполагается, что слот с slotId доступен
        TimeSlot slot = appointmentService.bookSlot(slotId, patientIdInDB);
        assertNotNull(slot.getPatient().getId(), "Слот должен быть забронирован");
        assertEquals(slotId, slot.getId(), "Идентификатор слота должен совпадать");
        assertEquals(patientIdInDB, slot.getPatient().getId(), "Идентификатор пациента должен совпадать");

        Long slotNotExistId = 110L;

        // Предполагается, что слот с slotId не существует
        assertThrows(SlotNotFoundException.class, () -> {
            appointmentService.bookSlot(slotNotExistId, patientIdInDB);
        }, "Должно выброситься исключение, указывающее, что слот уже занят");

        Long slotNotCorrectId = 10L;

        // Предполагается, что слот с slotId занят
        assertThrows(SlotIsBusyException.class, () -> {
            appointmentService.bookSlot(slotNotCorrectId, patientIdInDB);
        }, "Должно выброситься исключение, указывающее, что слот уже занят");
    }


    @Test
    void findSlotsByPatientByIdTest() {

        // Вызываем метод для поиска слотов
        List<TimeSlotDTO> bookedSlots = appointmentService.findSlotsByPatientById(patientIdInDB);

        // Проверяем количество найденных слотов
        assertEquals(1, bookedSlots.size(), "Должен быть найден один слот");

        // Проверяем, что найденный слот принадлежит указанному пациенту
        var bookedSlot = bookedSlots.get(0);
        // Проверяем детали слота
        assertEquals("Пациент Петров", bookedSlot.getPatientFullName(), "ФИО пациента должно совпадать");
        assertEquals("11:00", bookedSlot.getStartTime().toString(), "Время начала слота должно совпадать");
        assertEquals("12:00", bookedSlot.getEndTime().toString(), "Время окончания слота должно совпадать");
        assertEquals(LocalDate.of(2024, 1, 1), bookedSlot.getDate(), "Дата слота должна совпадать");
    }


    @Test
    void findSlotsByPatientByUuidTest() {
        UUID patientUuid = UUID.fromString("5517f51a-ad5f-41d7-870f-450772937437"); // UUID пациента

        // Вызываем метод для поиска слотов
        List<TimeSlotDTO> bookedSlots = appointmentService.findSlotsByPatientByUuid(patientUuid);

        // Проверяем количество найденных слотов
        assertEquals(1, bookedSlots.size(), "Должен быть найден один слот");

        // Проверяем, что найденный слот принадлежит указанному пациенту
        var bookedSlot = bookedSlots.get(0);
        // Проверяем детали слота
        assertEquals("Пациент Петров", bookedSlot.getPatientFullName(), "ФИО пациента должно совпадать");
        assertEquals("11:00", bookedSlot.getStartTime().toString(), "Время начала слота должно совпадать");
        assertEquals("12:00", bookedSlot.getEndTime().toString(), "Время окончания слота должно совпадать");
        assertEquals(LocalDate.of(2024, 1, 1), bookedSlot.getDate(), "Дата слота должна совпадать");
    }
}

