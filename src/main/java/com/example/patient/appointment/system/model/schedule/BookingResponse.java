package com.example.patient.appointment.system.model.schedule;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookingResponse {

    @XmlElement(name = "result")
    private String result;

    @XmlElement(name = "message")
    private String message;

    public BookingResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }
}
