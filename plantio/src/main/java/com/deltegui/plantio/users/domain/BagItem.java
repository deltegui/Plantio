package com.deltegui.plantio.users.domain;

import com.deltegui.plantio.game.domain.PlantType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BagItem {
    private final @NotNull PlantType item;
    private @NotNull @Min(1) int amount;

    public BagItem(PlantType item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    public boolean canSubstract(int amount) {
        return this.amount >= amount;
    }

    public void substract(int amount) {
        if (! this.canSubstract(amount)) return;
        this.amount -= amount;
    }

    public boolean amountIsZero() {
        return this.amount == 0;
    }

    public String getItem() {
        return this.item.name();
    }

    public int getAmount() {
        return this.amount;
    }
}
