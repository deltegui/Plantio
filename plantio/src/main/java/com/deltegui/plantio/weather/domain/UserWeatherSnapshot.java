package com.deltegui.plantio.weather.domain;

public final class UserWeatherSnapshot {
    private final String userName;
    private final WeatherReport report;

    public UserWeatherSnapshot(String userName, WeatherReport report) {
        this.userName = userName;
        this.report = report;
    }

    public String getUserName() {
        return userName;
    }

    public WeatherReport getReport() {
        return report;
    }
}
