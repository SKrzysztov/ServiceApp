package com.example.project.tools;

import com.example.project.customservice.domain.CustomService;

import java.time.LocalDateTime;

public class ReservationTimeCalculator {
    public static LocalDateTime calculateEndDateTime(LocalDateTime startDateTime, CustomService service) {
        return startDateTime.plus(service.getDuration());
    }
}
