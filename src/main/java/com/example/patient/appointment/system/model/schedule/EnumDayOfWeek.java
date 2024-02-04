package com.example.patient.appointment.system.model.schedule;

import java.time.DayOfWeek;

public enum EnumDayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayOfWeek toDayOfWeek(EnumDayOfWeek enumDay) {
        return switch (enumDay) {
            case MONDAY -> DayOfWeek.MONDAY;
            case TUESDAY -> DayOfWeek.TUESDAY;
            case WEDNESDAY -> DayOfWeek.WEDNESDAY;
            case THURSDAY -> DayOfWeek.THURSDAY;
            case FRIDAY -> DayOfWeek.FRIDAY;
            case SATURDAY -> DayOfWeek.SATURDAY;
            case SUNDAY -> DayOfWeek.SUNDAY;
            default -> throw new IllegalArgumentException("Unknown EnumDayOfWeek: " + enumDay);
        };
    }
}
