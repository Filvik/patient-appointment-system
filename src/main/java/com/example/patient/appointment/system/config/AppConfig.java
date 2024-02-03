package com.example.patient.appointment.system.config;

import com.example.patient.appointment.system.repository.DoctorRepository;
import com.example.patient.appointment.system.repository.TimeSlotRepository;
import com.example.patient.appointment.system.schedule.service.ScheduleServiceImpl;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("file:external.properties")
@RequiredArgsConstructor
public class AppConfig {

private final TimeSlotRepository timeSlotRepository;
private final DoctorRepository doctorRepository;

    @Bean
    @Profile("!test")
    public Endpoint endpoint() {
        return Endpoint.publish("http://localhost:8090/ScheduleService",
                new ScheduleServiceImpl(timeSlotRepository, doctorRepository));
    }
}
