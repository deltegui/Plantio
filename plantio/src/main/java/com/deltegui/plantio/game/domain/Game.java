package com.deltegui.plantio.game.domain;

import com.deltegui.plantio.weather.domain.WeatherReport;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
    private final String owner;
    private LocalDateTime lastUpdate;

    private Set<Plant> crop;

    public Game(String owner, LocalDateTime lastUpdate, Set<Plant> crop) {
        this.owner = owner;
        this.lastUpdate = lastUpdate;
        this.crop = crop;
    }

    public static Game createWithCrop(String owner, Set<Plant> crop) {
        return new Game(owner, LocalDateTime.now(), crop);
    }

    public static Game createEmpty(String owner) {
        return new Game(owner, LocalDateTime.now(), new HashSet<>());
    }

    public Game copy() {
        var copiedCrop = this.crop
                .stream()
                .map(Plant::copy)
                .collect(Collectors.toSet());
        return new Game(
                this.owner,
                this.lastUpdate,
                copiedCrop
        );
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
}
