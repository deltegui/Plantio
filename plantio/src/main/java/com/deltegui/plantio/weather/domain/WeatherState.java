package com.deltegui.plantio.weather.domain;

public enum WeatherState {
    CLEAR(1),
    RAIN(-10),
    CLOUDS(0.6f),
    SNOW(-5);
    private final float multiplier;

    WeatherState(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public static WeatherState fromString(String state) {
        switch (state) {
            case "Rain":
            case "Drizzle":
                return WeatherState.RAIN;
            case "Snow":
                return WeatherState.SNOW;
            case "Clouds":
            case "Mist":
                return WeatherState.CLOUDS;
            default:
                return WeatherState.CLEAR;
        }
    }
}
