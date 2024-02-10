package com.example.patient.appointment.system.model.schedule;

import com.example.patient.appointment.system.adapter.LocalDateAdapter;
import com.example.patient.appointment.system.adapter.LocalTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
@Data
public class ScheduleRequest {
    private Long doctorId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate bookingDate;
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime startWorkTime;
    private int slotDurationInMinutes;
    private int breakForLunchInMinutes;
    private int workingTimeInHoursInDay;
}
