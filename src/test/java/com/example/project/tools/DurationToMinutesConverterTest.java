package com.example.project.tools;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DurationToMinutesConverterTest {

    private final DurationToMinutesConverter converter = new DurationToMinutesConverter();

    @Test
    void shouldConvertToDatabaseColumn() {
        Duration duration = Duration.ofMinutes(60);
        Long result = converter.convertToDatabaseColumn(duration);
        assertEquals(60L, result);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        Long minutes = 120L;
        Duration result = converter.convertToEntityAttribute(minutes);
        assertEquals(Duration.ofMinutes(120), result);
    }

    @Test
    void shouldHandleNullValues() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }
}
