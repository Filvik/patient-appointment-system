package com.example.patient.appointment.system.model;

import com.example.patient.appointment.system.model.enumForTable.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Data
public class Patient {

    @Schema(description = "Идентификатор")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "UUID")
    @Column(updatable = false, unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Schema(description = "Полное имя")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Schema(description = "Пол")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @Schema(description = "Дата рождения")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Schema(description = "Номер страхового полиса")
    @Column(name = "insurance_policy_number", unique = true, nullable = false)
    private Long insurancePolicyNumber;

    @Schema(description = "Номер телефона")
    @Column(name = "phone_number")
    private String phoneNumber;

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
