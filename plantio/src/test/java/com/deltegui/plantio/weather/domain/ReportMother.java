package com.deltegui.plantio.weather.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ReportMother {
    public static WeatherReport createReport(WeatherState state, double temperature) {
        return new WeatherReport(
                new Coordinate(0, 0),
                "",
                state,
                temperature,
                0,
                0
        );
    }

    public static WeatherReport createReport(WeatherState state, double temperature, long hours) {
        return new WeatherReport(
                new Coordinate(0, 0),
                "",
                state,
                LocalDateTime.now().minus(hours, ChronoUnit.HOURS),
                temperature,
                0,
                0
        );
    }
}
