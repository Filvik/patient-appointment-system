package com.example.patient.appointment.system.adapter;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.TimeSlotDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TimeSlotConverter {

    private TimeSlotConverter() {
    }

    public static Optional<TimeSlotDTO> convertToDTO(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return Optional.empty();
        }

        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setId(timeSlot.getId());
        dto.setDoctorFullName(timeSlot.getDoctor().getFullName());
        dto.setPatientFullName(timeSlot.getPatient() != null ? timeSlot.getPatient().getFullName() : null);
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setDate(timeSlot.getDate());
        return Optional.of(dto);
    }

    public static List<TimeSlotDTO> convertToDTO(List<TimeSlot> timeSlots) {

        if (timeSlots == null || timeSlots.isEmpty()) {
            return Collections.emptyList();
        }

        return timeSlots.stream()
                .map(TimeSlotConverter::convertToDTO)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}

