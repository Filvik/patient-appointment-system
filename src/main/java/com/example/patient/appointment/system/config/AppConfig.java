package com.example.patient.appointment.system.config;

import com.example.patient.appointment.system.service.schedule.*;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурация Spring для создания и регистрации бинов сервисов расписания и публикации SOAP эндпоинтов.
 * Этот класс конфигурации использует {@link TimeSlotService} для инициализации различных сервисов расписания.
 * Он также определяет SOAP эндпоинты для доступа к этим сервисам через веб.
 */
@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("file:external.properties")
@RequiredArgsConstructor
public class AppConfig {

    private final TimeSlotService timeSlotService;

    /**
     * Создает бин сервиса для управления ежедневным расписанием.
     *
     * @return {@link ScheduleDayService} сервис для управления ежедневным расписанием.
     */
    @Bean
    public ScheduleDayService scheduleDayService() {
        return new ScheduleDayService(timeSlotService);
    }

    /**
     * Создает бин сервиса для управления недельным расписанием.
     *
     * @param scheduleDayService Сервис для управления ежедневным расписанием, используемый для генерации недельного расписания.
     * @return {@link ScheduleWeekService} сервис для управления недельным расписанием.
     */
    @Bean
    public ScheduleWeekService scheduleWeekService(ScheduleDayService scheduleDayService) {
        return new ScheduleWeekService(timeSlotService);
    }

    /**
     * Создает бин сервиса для управления расписанием на сложную неделю.
     *
     * @return {@link ScheduleDifficultWeekService} сервис для управления расписанием на сложную неделю.
     */
    @Bean
    public ScheduleDifficultWeekService scheduleRequestOfWeek() {
        return new ScheduleDifficultWeekService(timeSlotService);
    }

    /**
     * Создает SOAP эндпоинт для сервиса управления ежедневным расписанием.
     *
     * @param scheduleDayService Сервис для управления ежедневным расписанием.
     * @return {@link Endpoint} - SOAP эндпоинт для сервиса управления ежедневным расписанием.
     */
    @Bean
    @Profile("!test")
    public Endpoint endPointForDay(ScheduleDayService scheduleDayService) {
        return Endpoint.publish("http://localhost:8090/ScheduleDayService", scheduleDayService);
    }

    /**
     * Создает SOAP эндпоинт для сервиса управления недельным расписанием.
     *
     * @param scheduleWeekService Сервис для управления недельным расписанием.
     * @return {@link Endpoint} - SOAP эндпоинт для сервиса управления недельным расписанием.
     */
    @Bean
    @Profile("!test")
    public Endpoint endPointWeek(ScheduleWeekService scheduleWeekService) {
        return Endpoint.publish("http://localhost:8090/ScheduleWeekService", scheduleWeekService);
    }

    /**
     * Создает SOAP эндпоинт для сервиса управления расписанием на сложную неделю.
     *
     * @param scheduleServiceOfWeek Сервис для управления расписанием на сложную неделю.
     * @return {@link Endpoint} - SOAP эндпоинт для сервиса управления расписанием на сложную неделю.
     */
    @Bean
    @Profile("!test")
    public Endpoint endPointWeekDifficultWeek(ScheduleDifficultWeekService scheduleServiceOfWeek) {
        return Endpoint.publish("http://localhost:8090/ScheduleDifficultWeekService", scheduleServiceOfWeek);
    }

    /**
     * Создает бин сервиса для управления объединенным расписанием.
     *
     * @param scheduleDayService           Сервис для управления ежедневным расписанием.
     * @param scheduleWeekService          Сервис для управления недельным расписанием.
     * @param scheduleDifficultWeekService Сервис для управления расписанием на сложную неделю.
     * @return {@link UnifiedScheduleService} сервис для управления объединенным расписанием.
     */

    @Bean
    public UnifiedScheduleService unifiedScheduleService(ScheduleDayService scheduleDayService,
                                                         ScheduleWeekService scheduleWeekService,
                                                         ScheduleDifficultWeekService scheduleDifficultWeekService) {
        return new UnifiedScheduleServiceImpl(scheduleDayService, scheduleWeekService, scheduleDifficultWeekService);
    }

    /**
     * Создает SOAP эндпоинт для сервиса управления объединенным расписанием.
     *
     * @param unifiedScheduleService Сервис для управления объединенным расписанием.
     * @return {@link Endpoint} - SOAP эндпоинт для сервиса управления объединенным расписанием.
     */
    @Bean
    @Profile("!test")
    public Endpoint unifiedScheduleServiceEndpoint(UnifiedScheduleService unifiedScheduleService) {
        return Endpoint.publish("http://localhost:8090/UnifiedScheduleService", unifiedScheduleService);
    }
}
