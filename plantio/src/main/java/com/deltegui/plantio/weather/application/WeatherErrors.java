package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.common.DomainError;

public enum WeatherErrors implements DomainError {
    Read(200, "Cannot get weather information for location"),;
    private final int code;
    private final String message;

    WeatherErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
