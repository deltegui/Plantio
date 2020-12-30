package com.deltegui.plantio.store.domain;

import com.deltegui.plantio.game.domain.PlantType;

public class Seeds {
    private final PlantType type;
    private final int amount;

    public Seeds(PlantType plantType, int amount) {
        this.type = plantType;
        this.amount = amount;
    }

    public PlantType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
