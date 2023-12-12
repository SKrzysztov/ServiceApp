package com.example.project.tools;

import java.time.Duration;

public class DurationToMinutesConverter {

    public static long convertToMinutes(Duration duration) {
        return duration.toMinutes();
    }

    public static Duration convertToDuration(long minutes) {
        return Duration.ofMinutes(minutes);
    }
}
