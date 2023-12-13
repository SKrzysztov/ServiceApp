package com.example.project.tools;

import com.example.project.customservice.domain.CustomService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTimeCalculatorTest {

    @Test
    void shouldCalculateEndDateTime() {
        LocalDateTime startDateTime = LocalDateTime.parse("2023-12-12T12:00:00");
        CustomService service = new CustomService();
        service.setDuration(Duration.ofMinutes(30));

        LocalDateTime result = ReservationTimeCalculator.calculateEndDateTime(startDateTime, service);

        assertEquals(LocalDateTime.parse("2023-12-12T12:30:00"), result);
    }
}
