package com.example.expense_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private final int logRounds = 12;

    @Bean
    public BCryptConfig bCryptConfig() {
        return new BCryptConfig(logRounds);
    }
}