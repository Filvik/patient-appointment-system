package com.example.patient.appointment.system.adapter;

import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.TimeSlotDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeSlotConverterWithUseModelMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    // Конвертер для TimeSlot -> TimeSlotDTO
    public static TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDTO.class);
    }

    // Конвертер для List<TimeSlot> -> List<TimeSlotDTO>
    public static List<TimeSlotDTO> convertToDTO(List<TimeSlot> timeSlots) {
        return timeSlots.stream()
                .map(TimeSlotConverterWithUseModelMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
