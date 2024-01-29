package com.example.patient.appointment.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("file:external.properties")
public class AppConfig {
}
