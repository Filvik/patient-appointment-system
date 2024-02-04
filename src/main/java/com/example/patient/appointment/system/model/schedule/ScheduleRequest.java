package com.example.patient.appointment.system.model.schedule;

import com.example.patient.appointment.system.adapter.LocalDateAdapter;
import com.example.patient.appointment.system.adapter.LocalTimeAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;


@XmlRootElement(name = "ScheduleRequest")
public class ScheduleRequest {
    private Long doctorId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate bookingDate;
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime startWorkTime;
    private int slotDurationInMinutes;
    private int breakForLunchInMinutes;
    private int workingTimeInHoursInDay;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    @XmlTransient
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    @XmlTransient
    public LocalTime getStartWorkTime() {
        return startWorkTime;
    }
    public void setStartWorkTime(LocalTime startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public int getSlotDurationInMinutes() {
        return slotDurationInMinutes;
    }

    public void setSlotDurationInMinutes(int slotDurationInMinutes) {
        this.slotDurationInMinutes = slotDurationInMinutes;
    }

    public int getBreakForLunchInMinutes() {
        return breakForLunchInMinutes;
    }

    public void setBreakForLunchInMinutes(int breakForLunchInMinutes) {
        this.breakForLunchInMinutes = breakForLunchInMinutes;
    }

    public int getWorkingTimeInHoursInDay() {
        return workingTimeInHoursInDay;
    }

    public void setWorkingTimeInHoursInDay(int workingTimeInHoursInDay) {
        this.workingTimeInHoursInDay = workingTimeInHoursInDay;
    }
}
