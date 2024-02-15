package com.example.patient.appointment.system.repository;

import com.example.patient.appointment.system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUuid(UUID uuid);
    Optional<Patient> findByFullNameAndDateOfBirth(String fullName, LocalDate dateOfBirth);
}
