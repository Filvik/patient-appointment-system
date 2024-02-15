package com.example.patient.appointment.system.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "time_slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "date", "start_time"})
})
@Data
public class TimeSlot {

    @Schema(description = "Идентификатор")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Идентификатор для доктора")
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Schema(description = "Идентификатор для пациента")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Schema(description = "Время начала")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Schema(description = "Время окончания")
    @Column(name = "end_time")
    private LocalTime endTime;

    @Schema(description = "Дата")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Schema(description = "Создано")
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Schema(description = "Обновлено")
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
