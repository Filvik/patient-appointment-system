package com.example.patient.appointment.system.config;

import com.example.patient.appointment.system.model.Doctor;
import com.example.patient.appointment.system.model.Patient;
import com.example.patient.appointment.system.model.TimeSlot;
import com.example.patient.appointment.system.model.TimeSlotDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Конвертер для Doctor -> doctorFullName
        Converter<Doctor, String> doctorToStringConverter = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getFullName();

        //Конвертер для Patient -> patientFullName
        Converter<Patient, String> patientToStringConverter = ctx ->
                ctx.getSource() == null ? null : ctx.getSource().getFullName();

        modelMapper.typeMap(TimeSlot.class, TimeSlotDTO.class).addMappings(mapper -> {
            mapper.using(doctorToStringConverter).map(TimeSlot::getDoctor, TimeSlotDTO::setDoctorFullName);
            mapper.using(patientToStringConverter).map(TimeSlot::getPatient, TimeSlotDTO::setPatientFullName);
        });

        return modelMapper;
    }
}

