package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Plant;

import java.util.Set;

public final class SaveRequest {
    private final String user;
    private final Set<Plant> plants;

    public SaveRequest(String user, Set<Plant> plants) {
        this.user = user;
        this.plants = plants;
    }

    public String getUser() {
        return user;
    }

    public Set<Plant> getPlants() {
        return plants;
    }
}
