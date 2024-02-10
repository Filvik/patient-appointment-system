package com.example.patient.appointment.system.service.schedule;

import com.example.patient.appointment.system.model.schedule.BookingResponse;
import com.example.patient.appointment.system.model.schedule.ScheduleRequest;
import com.example.patient.appointment.system.model.schedule.ScheduleRequestOfWeek;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService
public interface UnifiedScheduleService {

    /**
     * Создает временные слоты для заданного дня.
     *
     * @param request данные запроса для создания слотов на день
     * @return ответ с результатом создания слотов
     */
    @WebMethod
    @WebResult(name = "bookingResponse")
    BookingResponse createTimeSlotsForDayWithResponse(ScheduleRequest request);

    /**
     * Создает временные слоты для заданной недели.
     *
     * @param request данные запроса для создания слотов на неделю
     * @return ответ с результатом создания слотов
     */
    @WebMethod
    @WebResult(name = "bookingResponse")
    BookingResponse createTimeSlotsForWeekWithResponse(ScheduleRequest request);

    /**
     * Создает временные слоты для "сложной" недели с особыми условиями.
     *
     * @param request данные запроса для создания слотов на сложную неделю
     * @return ответ с результатом создания слотов
     */
    @WebMethod
    @WebResult(name = "bookingResponse")
    BookingResponse createTimeSlotsForDifficultWeekWithResponse(ScheduleRequestOfWeek request);
}
