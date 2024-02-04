package com.example.patient.appointment.system.schedule.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;


public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v); // Преобразует строку в LocalDate
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString(); // Преобразует LocalDate в строку
    }
}

