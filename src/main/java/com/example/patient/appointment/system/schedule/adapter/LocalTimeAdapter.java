package com.example.patient.appointment.system.schedule.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalTime;


public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v); // Преобразует строку в LocalTime
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v.toString(); // Преобразует LocalTime в строку
    }
}
