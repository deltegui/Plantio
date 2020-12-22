package com.deltegui.plantio.game.application;

import com.deltegui.plantio.game.domain.Plant;

import java.util.HashSet;
import java.util.Set;

public final class SaveRequest {
    private final String user;
    private final Set<PlantRequest> plants;

    public SaveRequest(String user, Set<PlantRequest> plants) {
        this.user = user;
        this.plants = plants;
    }

    public Set<Plant> merge(Set<Plant> plants) {
        Set<Plant> result = new HashSet<>();
        for (PlantRequest plantRequest : this.plants) {
            result.add(createPlant(plants, plantRequest));
        }
        return result;
    }

    private Plant createPlant(Set<Plant> plants, PlantRequest plantRequest) {
        for (Plant plant : plants) {
            if (plantRequest.comesFromPlant(plant)) {
                return plantRequest.merge(plant);
            }
        }
        return plantRequest.createNewPlant();
    }

    public String getUser() {
        return user;
    }

    public Set<PlantRequest> getPlants() {
        return plants;
    }
}
