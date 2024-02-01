package com.example.patient.appointment.system.repository;

import com.example.patient.appointment.system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Collection<Object> findByUuid(UUID uuid);
}
