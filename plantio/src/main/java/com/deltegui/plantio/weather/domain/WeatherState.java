package com.deltegui.plantio.weather.domain;

public enum WeatherState {
    CLEAR,
    RAIN,
    CLOUDS,
    SNOW;

    public static WeatherState fromString(String state) {
        switch (state) {
            case "Rain": return WeatherState.RAIN;
            case "Snow": return WeatherState.SNOW;
            case "Clouds": return WeatherState.CLOUDS;
            default: return WeatherState.CLEAR;
        }
    }
}
