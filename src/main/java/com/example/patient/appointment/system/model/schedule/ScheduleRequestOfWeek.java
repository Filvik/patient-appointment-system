package com.example.patient.appointment.system.model.schedule;

import com.example.patient.appointment.system.adapter.EnumDayOfWeekAdapter;
import com.example.patient.appointment.system.adapter.LocalDateAdapter;
import com.example.patient.appointment.system.adapter.LocalTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@XmlRootElement(name = "ScheduleRequestOfWeek")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ScheduleRequestOfWeek {

    private Long doctorId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateTo;
    private int slotDurationInMinutes;
    @XmlElement(name = "scheduleRequestOfDay")
    private List<ScheduleRequestOfDay> scheduleRequestOfDayList;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class ScheduleRequestOfDay {

        @XmlElement
        @XmlJavaTypeAdapter(EnumDayOfWeekAdapter.class)
        private EnumDayOfWeek dayOfWeek;

        @XmlElement
        private ScheduleRequestDayOfWeek scheduleRequest;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    public static class ScheduleRequestDayOfWeek {
        @XmlJavaTypeAdapter(LocalTimeAdapter.class)
        private LocalTime startWorkTime;
        private int breakForLunchInMinutes;
        private int workingTimeInHoursInDay;
    }
}

