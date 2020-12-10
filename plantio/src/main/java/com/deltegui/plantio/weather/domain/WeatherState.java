package com.deltegui.plantio.weather.domain;

public enum WeatherState {
    CLEAR,
    RAIN,
    CLOUDS,
    SNOW;

    public static WeatherState fromString(String state) {
        switch (state) {
            case "RAIN": return WeatherState.RAIN;
            case "SNOW": return WeatherState.SNOW;
            case "CLOUDS": return WeatherState.CLOUDS;
            default: return WeatherState.CLEAR;
        }
    }
}
