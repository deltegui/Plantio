package com.deltegui.plantio.weather.domain;

import com.deltegui.plantio.game.domain.Plant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WeatherReport {
    private final Coordinate coordinate;
    private final String location;
    private final WeatherState state;
    private final double temperature;
    private final LocalDateTime creation;
    private final int sunrise;
    private final int sunset;

    public WeatherReport(Coordinate coordinate, String location, WeatherState state, double temperature, int sunrise, int sunset) {
        this.coordinate = coordinate;
        this.location = location;
        this.state = state;
        this.temperature = temperature;
        this.creation = LocalDateTime.now();
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    WeatherReport(Coordinate coordinate, String location, WeatherState state, LocalDateTime dateTime, double temperature, int sunrise, int sunset) {
        this.coordinate = coordinate;
        this.location = location;
        this.state = state;
        this.temperature = temperature;
        this.creation = dateTime;
        this.sunrise = sunrise;
        this.sunset = sunset;
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

    public boolean isInInterval(TemperatureInterval interval) {
        return interval.includes(this.temperature);
    }

    public double calculateHumidity(Plant plant) {
        double oldHumidity = plant.getHumidity();
        double plantFactor = plant.getHumidityGrowthFactor();
        final long hoursElapsed = this.calculateHoursElapsed(plant);
        double factor;
        if (this.temperature < 0) {
            factor = this.calculateFactorForNegativeTemperature(plantFactor, hoursElapsed);
        } else {
            factor = this.calculateFactorForPositiveTemperature(plantFactor, hoursElapsed);
        }
        return calculateHumidity(oldHumidity, factor);
    }

    private double calculateHumidity(double oldHumidity, double factor) {
        double humidity = oldHumidity - (100 * factor);
        if (humidity < 0) {
            humidity = 0;
        }
        if (humidity > 100) {
            humidity = 100;
        }
        return Math.round(humidity * 100.0) / 100.0;
    }

    private long calculateHoursElapsed(Plant plant) {
        return this.calculateHoursFrom(plant.getLastAppliedReportDate());
    }

    private double calculateFactorForNegativeTemperature(double plantFactor, long hoursElapsed) {
        final int referenceHours = 100;
        return (Math.abs(this.temperature) / referenceHours) * plantFactor * hoursElapsed * this.state.getMultiplier();
    }

    private double calculateFactorForPositiveTemperature(double plantFactor, long hoursElapsed) {
        final int referenceHours = 50;
        double f = (this.temperature / referenceHours) * plantFactor * hoursElapsed * this.state.getMultiplier();
        return f + Math.pow(plantFactor, 2);
    }

    public long calculateHoursFrom(LocalDateTime other) {
        return ChronoUnit.HOURS.between(other, creation);
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

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public LocalDateTime getCreation() {
        return creation;
    }
}
