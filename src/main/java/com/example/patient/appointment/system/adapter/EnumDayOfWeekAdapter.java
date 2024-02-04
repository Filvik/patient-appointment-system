package com.example.patient.appointment.system.adapter;

import com.example.patient.appointment.system.model.schedule.EnumDayOfWeek;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class EnumDayOfWeekAdapter extends XmlAdapter<String, EnumDayOfWeek> {

    @Override
    public EnumDayOfWeek unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return EnumDayOfWeek.valueOf(v.toUpperCase());
    }

    @Override
    public String marshal(EnumDayOfWeek v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.name().toLowerCase();
    }
}
