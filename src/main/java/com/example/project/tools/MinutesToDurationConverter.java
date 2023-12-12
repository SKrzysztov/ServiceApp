package com.example.project.tools;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter
public class MinutesToDurationConverter implements AttributeConverter<Long, Duration> {

    @Override
    public Duration convertToDatabaseColumn(Long minutes) {
        return Duration.ofMinutes(minutes);
    }

    @Override
    public Long convertToEntityAttribute(Duration duration) {
        return duration.toMinutes();
    }
}