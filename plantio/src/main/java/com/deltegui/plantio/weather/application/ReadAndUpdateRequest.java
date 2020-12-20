package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.weather.domain.Coordinate;

public final class ReadAndUpdateRequest {
    private final String username;
    private final Coordinate coordinate;

    public ReadAndUpdateRequest(String username, Coordinate coordinate) {
        this.username = username;
        this.coordinate = coordinate;
    }

    public String getUsername() {
        return username;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
