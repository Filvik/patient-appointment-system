package com.example.patient.appointment.system.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Ответ на запрос")
@Data
@AllArgsConstructor
public class ResponseForSpecificPatient {

    @JsonProperty("existed")
    @Schema(description = "Существовал ли пациент с таким ID в базе данных")
    private boolean existed;
    @JsonProperty("added")
    @Schema(description = "Добавлен ли пациент в базу данных")
    private boolean added;
    @JsonProperty("is_slot_booked")
    @Schema(description = "Забронирован ли слот времени")
    private Boolean isSlotBooked;
    @JsonProperty("description")
    @Schema(description = "Ошибка")
    private String description;
}
