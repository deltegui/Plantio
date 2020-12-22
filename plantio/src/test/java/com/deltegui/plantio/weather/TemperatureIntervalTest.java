package com.deltegui.plantio.weather;

import com.deltegui.plantio.weather.domain.TemperatureInterval;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TemperatureIntervalTest {
    @ParameterizedTest
    @MethodSource
    public void shouldCheckIncludedElement(double min, double max, double element, boolean expected) {
        var interval = TemperatureInterval.withMinMax(min, max);
        assertEquals(expected, interval.includes(element));
    }

    public static Stream<Arguments> shouldCheckIncludedElement() {
        return Stream.of(
                arguments(10, 20, 15, true),
                arguments(15, 30, 15, true),
                arguments(15, 30, 16, true),
                arguments(15, 30, 14, false),
                arguments(0, 9, 9, true),
                arguments(0, 9, 8, true),
                arguments(0, 9, 10, false),
                arguments(0, 9, -10, false),
                arguments(20, 40, 42, false)
        );
    }
}
