package com.deltegui.plantio.weather.domain;

public enum WeatherState {
    Clear,
    Rain,
    Clouds,
    Snow;

    public static WeatherState fromString(String state) {
        switch (state) {
            case "Rain": return WeatherState.Rain;
            case "Snow": return WeatherState.Snow;
            case "Clouds": return WeatherState.Clouds;
            default: return WeatherState.Clear;
        }
    }
}
