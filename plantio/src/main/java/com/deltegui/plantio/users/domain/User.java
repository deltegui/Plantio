package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.weather.domain.Coordinate;

import java.util.Optional;

public class User {
    private final String name;
    private final String password;
    private Coordinate lastPosition;

    public User(String name, String password, Coordinate lastPosition) {
        this.name = name;
        this.password = password;
        this.lastPosition = lastPosition;
    }

    public User(String name, String password) {
        this(name, password, null);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Optional<Coordinate> getLastPosition() {
        return this.lastPosition == null ? Optional.empty() : Optional.of(this.lastPosition);
    }

    public void setLastPosition(Coordinate updatedPosition) {
        this.lastPosition = updatedPosition;
    }
}
