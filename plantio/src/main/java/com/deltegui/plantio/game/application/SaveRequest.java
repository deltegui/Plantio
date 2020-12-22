package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Plant;

import java.util.Optional;
import java.util.Set;

public final class SaveRequest {
    private final String user;
    private final Set<PlantRequest> plants;

    public SaveRequest(String user, Set<PlantRequest> plants) {
        this.user = user;
        this.plants = plants;
    }

    public Optional<Plant> createPlantIfExists(Plant plant) {
        for (PlantRequest plantRequest : this.plants) {
            if (plantRequest.comesFromPlant(plant)) {
                return Optional.of(plantRequest.merge(plant));
            }
        }
        return Optional.empty();
    }

    public String getUser() {
        return user;
    }

    public Set<PlantRequest> getPlants() {
        return plants;
    }
}
