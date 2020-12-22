package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.Coordinate;
import com.deltegui.plantio.weather.domain.WeatherReport;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Game {
    private final String owner;
    private LocalDateTime lastUpdate;
    private Coordinate lastPosition;
    private Set<Plant> crop;

    public Game(String owner, LocalDateTime lastUpdate, Coordinate lastPosition, Set<Plant> crop) {
        this.owner = owner;
        this.lastUpdate = lastUpdate;
        this.crop = crop;
        this.lastPosition = lastPosition;
    }

    public static Game createWithCrop(String owner, Set<Plant> crop) {
        return new Game(owner, LocalDateTime.now(), null, crop);
    }

    public static Game createEmpty(String owner) {
        return new Game(owner, LocalDateTime.now(), null, new HashSet<>());
    }

    public void replaceCrop(Set<Plant> crop) {
        this.crop = crop;
        this.lastUpdate = LocalDateTime.now();
    }

    public void applyWeather(WeatherReport report) {
        for (Plant plant : this.crop) {
            plant.applyWeather(report);
        }
    }

    public String getOwner() {
        return owner;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public Set<Plant> getCrop() {
        return crop;
    }

    public void setCrop(Set<Plant> crop) {
        this.crop = crop;
    }

    public Optional<Coordinate> getLastPosition() {
        return this.lastPosition == null ? Optional.empty() : Optional.of(this.lastPosition);
    }

    public void setLastPosition(Coordinate updatedPosition) {
        this.lastPosition = updatedPosition;
    }
}
