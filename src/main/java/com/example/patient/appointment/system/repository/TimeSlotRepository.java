package com.example.patient.appointment.system.repository;

import com.example.patient.appointment.system.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByDoctorIdAndDate(Long doctorId, LocalDate date);

    List<TimeSlot> findSlotsByPatientId(Long patientId);

    List<TimeSlot> findByPatientUuid(UUID patientUuid);
}
