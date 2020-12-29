package com.deltegui.plantio.weather.application;

import com.deltegui.plantio.weather.domain.Coordinate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public final class ReadAndUpdateRequest {
    private final @NotNull @Length(max = 255, min = 2) String username;
    private final @NotNull Coordinate coordinate;

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
