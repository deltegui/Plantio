package com.deltegui.plantio.weather.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WeatherReport {
    private final Coordinate coordinate;
    private final String location;
    private final WeatherState state;
    private final double temperature;
    private final LocalDateTime creation;

    public WeatherReport(Coordinate coordinate, String location, WeatherState state, double temperature) {
        this.coordinate = coordinate;
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

    public boolean isNearTo(Coordinate other) {
        final int ACTION_RANGE = 10;
        AreaRange areaRange = coordinate.calculateAreaRange(ACTION_RANGE);
        AreaRange otherRange = other.calculateAreaRange(ACTION_RANGE);
        return areaRange.includes(otherRange);
    }

    public Coordinate getCoordinate() {
        return coordinate;
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
