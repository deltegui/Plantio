package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.game.domain.PlantType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Seeds {
    private final @NotNull PlantType item;
    private final @NotNull @Min(1) int amount;

    public Seeds(PlantType item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public String getItem() {
        return this.item.name();
    }
    public int getAmount() {
        return this.amount;
    }
}
