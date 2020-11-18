package com.deltegui.plantio.weather.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WeatherReport {
    private final String location;
    private final WeatherState state;
    private final double temperature;
    private final LocalDateTime creation;

    public WeatherReport(String location, WeatherState state, double temperature) {
        this.location = location;
        this.state = state;
        this.temperature = temperature;
        this.creation = LocalDateTime.now();
    }

    public boolean isOld() {
        var hourAgo = LocalDateTime.now()
                .minus(1, ChronoUnit.HOURS);
        return hourAgo.isAfter(creation);
    }

    public String getLocation() {
        return location;
    }

    public WeatherState getState() {
        return state;
    }

    public double getTemperature() {
        return temperature;
    }
}
