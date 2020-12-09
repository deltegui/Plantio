package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;

import java.time.LocalDateTime;
import java.util.Set;

public final class SaveResponse {
    private final Set<Plant> plants;
    private final LocalDateTime lastUpdate;

    public SaveResponse(Set<Plant> plants, LocalDateTime lastUpdate) {
        this.plants = plants;
        this.lastUpdate = lastUpdate;
    }

    public static SaveResponse fromGame(Game game) {
        return new SaveResponse(game.getCrop(), game.getLastUpdate());
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
