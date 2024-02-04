package com.example.patient.appointment.system.schedule.service;

import com.example.patient.appointment.system.model.Doctor;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.repository.DoctorRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import com.example.patient.appointment.system.schedule.request.ScheduleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleDayServiceImplTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private ScheduleDayServiceImpl scheduleService;

    private ScheduleRequest request;

    @BeforeEach
    void setUp() {
        request = new ScheduleRequest();
        request.setDoctorId(1L);
        request.setBookingDate(LocalDate.of(2022, 1, 1));
        request.setStartWorkTime(LocalTime.of(9, 0));
        request.setSlotDurationInMinutes(30);
        request.setBreakForLunchInMinutes(60);
        request.setWorkingTimeInHoursInDay(8);

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(mockDoctor));
    }

    @Test
    void createTimeSlots_ShouldCreateSlots_WhenRequestIsValid() {

        List<TimeSlot> slots = scheduleService.createTimeSlots(request);

        // Проверяем количество созданных слотов
        assertEquals(14, slots.size(), "Количество созданных слотов соответствует ожидаемому");

        request.setBookingDate(null);

        List<TimeSlot> dateNull = scheduleService.createTimeSlots(request);

        // Проверяем количество созданных слотов
        assertEquals(0, dateNull.size(), "Количество созданных слотов соответствует ожидаемому");

        request.setBookingDate(LocalDate.of(2022, 1, 1));

        // Изменяём ID доктора на несуществующий
        request.setDoctorId(150L);

        List<TimeSlot> slotNull = scheduleService.createTimeSlots(request);

        // Проверяем количество созданных слотов
        assertEquals(0, slotNull.size(), "Количество созданных слотов соответствует ожидаемому");
    }
}
